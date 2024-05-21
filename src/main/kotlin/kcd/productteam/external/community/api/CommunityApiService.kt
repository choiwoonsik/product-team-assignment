package kcd.productteam.external.community.api

import com.google.gson.JsonObject
import kcd.productteam.external.community.dto.PosEasyConnectionAgreementResult
import kcd.productteam.external.service.restTemplateService.RestTemplateService
import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CommunityApiService(
    private val restTemplateService: RestTemplateService,
) {
    companion object {
        private const val BASE_PATH = "https://aec71921-8957-4a79-a114-a1e5b9515382.mock.pstmn.io"
        private const val HAS_BUSINESS_PATH = "/has-business"
        private const val REGISTER_DATA_COMMUNICATION_PATH = "/register-data-communication"
        private const val PRODUCE_SALES_CARD_TRANSACTIONS_PATH = "/produce-sales-card-transactions"
    }

    fun checkHasBusiness(registrationNumber: String): Boolean {
        return restTemplateService.fetch(
            method = HttpMethod.GET,
            path = "$BASE_PATH$HAS_BUSINESS_PATH",
            requestEntity = null,
            queryParams = mapOf("registrationNumber" to registrationNumber),
            responseType = JsonObject::class.java,
        ).get("result").asBoolean
    }

    fun registerDataCommunication(posEasyConnectionAgreementResult: PosEasyConnectionAgreementResult) {
        restTemplateService.fetch(
            method = HttpMethod.POST,
            path = "$BASE_PATH$REGISTER_DATA_COMMUNICATION_PATH",
            requestEntity = RequestEntity(
                posEasyConnectionAgreementResult,
                HttpMethod.POST,
                URI.create("$BASE_PATH$REGISTER_DATA_COMMUNICATION_PATH")
            ),
            queryParams = emptyMap(),
            responseType = JsonObject::class.java,
        )
    }

    fun produceSalesCardTransactionList(posSalesCardTransactionEventDtoList: List<PosSalesCardTransactionEventDto>) {
        restTemplateService.fetch(
            method = HttpMethod.POST,
            path = "$BASE_PATH$PRODUCE_SALES_CARD_TRANSACTIONS_PATH",
            requestEntity = RequestEntity(
                posSalesCardTransactionEventDtoList,
                HttpMethod.POST,
                URI.create("$BASE_PATH$PRODUCE_SALES_CARD_TRANSACTIONS_PATH")
            ),
            queryParams = emptyMap(),
            responseType = JsonObject::class.java,
        )
    }
}