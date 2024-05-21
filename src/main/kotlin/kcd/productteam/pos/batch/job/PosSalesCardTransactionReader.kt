package kcd.productteam.pos.batch.job

import kcd.productteam.pos.batch.job.PosSalesCardTransactionConfiguration.Companion.START_REGISTRATION_NUMBER
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
        val startRegistrationNumber = executionContext.getString(START_REGISTRATION_NUMBER, "0")
        if (startRegistrationNumber != "0") {
            registrationNumberTargetList.filter { it > startRegistrationNumber }.toMutableList()
            registrationNumberTargetList.clear()
            registrationNumberTargetList.addAll(registrationNumberTargetList)
        }

        queue.addAll(registrationNumberTargetList.chunked(10))
        log.info("registrationNumberTargetList 개수: ${registrationNumberTargetList.size}")
    }

    override fun read(): List<PosSalesCardTransaction>? {
        val registrationNumbers = queue.poll() ?: return null

        return posSalesCardTransactionRepository.findAllPosSalesCardTransactionDaily(
            registrationNumbers = registrationNumbers,
            dataSource = PosSalesCardTransactionDataSource.CACHE_NOTE
        )
    }

    override fun update(executionContext: ExecutionContext) {
        if (queue.isNotEmpty()) {
            executionContext.putString(START_REGISTRATION_NUMBER, queue.peek().first())
            log.info("다음 시작 registrationNumber: ${queue.peek().first()}")
        }
    }
}