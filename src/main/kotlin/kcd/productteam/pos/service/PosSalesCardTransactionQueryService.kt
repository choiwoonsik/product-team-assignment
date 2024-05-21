package kcd.productteam.pos.service

import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource
import kcd.productteam.pos.repository.PosSalesCardTransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PosSalesCardTransactionQueryService(
    private val posSalesCardTransactionRepository: PosSalesCardTransactionRepository,
) {
    @Transactional(readOnly = true)
    fun findAllPosSalesCardTransactionDaily(
        registrationNumberList: List<String>,
        dataSource: PosSalesCardTransactionDataSource,
    ): List<PosSalesCardTransaction> {
        return posSalesCardTransactionRepository.findAllPosSalesCardTransactionDaily(
            registrationNumberList,
            dataSource
        )
    }

    @Transactional(readOnly = true)
    fun findAllPosSalesCardTransactionPastMonthHistory(
        pastMonth: Int,
        registrationNumber: String,
        dataSource: PosSalesCardTransactionDataSource,
    ): List<PosSalesCardTransaction> {
        return posSalesCardTransactionRepository.findAllPosSalesCardTransactionPastMonthHistory(
            pastMonth,
            registrationNumber,
            dataSource
        )
    }
}