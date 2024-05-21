package kcd.productteam.pos.controller

import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource.CACHE_NOTE
import kcd.productteam.pos.service.PosSalesCardTransactionQueryService
import kcd.productteam.utils.MultiResponse
import kcd.productteam.utils.getMultiResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/pos/sales")
class PosSalesCardTransactionController(
    private val posSalesCardTransactionQueryService: PosSalesCardTransactionQueryService,
) {
    companion object {
        private const val SIX_MONTH = 6
    }

    @GetMapping("/card-transactions/daily")
    fun posSalesCardTransactionsDaily(
        registrationNumber: String,
    ): MultiResponse<PosSalesCardTransaction> {
        val cardTransactionDaily =
            posSalesCardTransactionQueryService.findAllPosSalesCardTransactionDaily(
                listOf(registrationNumber),
                CACHE_NOTE
            )

        return getMultiResult(cardTransactionDaily)
    }

    @GetMapping("/card-transactions/past-month-history")
    fun posSalesCardTransactionsHistory(
        registrationNumber: String,
    ): MultiResponse<PosSalesCardTransaction> {
        val cardTransactionPastMonthHistory =
            posSalesCardTransactionQueryService.findAllPosSalesCardTransactionPastMonthHistory(
                SIX_MONTH,
                registrationNumber,
                CACHE_NOTE
            )

        return getMultiResult(cardTransactionPastMonthHistory)
    }
}