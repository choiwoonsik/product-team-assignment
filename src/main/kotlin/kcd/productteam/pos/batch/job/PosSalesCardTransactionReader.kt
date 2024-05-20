package kcd.productteam.pos.batch.job

import kcd.productteam.pos.batch.job.PosSalesCardTransactionConfiguration.Companion.registrationNumberTargetList
import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource
import kcd.productteam.pos.repository.PosSalesCardTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamReader
import java.util.LinkedList

class PosSalesCardTransactionReader(
    private val posSalesCardTransactionRepository: PosSalesCardTransactionRepository,
) : ItemStreamReader<List<PosSalesCardTransaction>> {
    private val queue: LinkedList<List<String>> = LinkedList()

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun open(executionContext: ExecutionContext) {
        queue.addAll(registrationNumberTargetList.chunked(50))

        log.info("registrationNumberTargetList 개수: ${registrationNumberTargetList.size}")
    }

    override fun read(): List<PosSalesCardTransaction>? {
        val registrationNumbers = queue.poll() ?: return null

        return posSalesCardTransactionRepository.findPosSalesCardTransactionDaily(
            registrationNumbers = registrationNumbers,
            dataSource = PosSalesCardTransactionDataSource.COMMUNITY
        )
    }
}