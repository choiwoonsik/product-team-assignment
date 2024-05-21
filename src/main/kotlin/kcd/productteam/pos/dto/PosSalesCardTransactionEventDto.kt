package kcd.productteam.pos.dto

import com.fasterxml.jackson.annotation.JsonProperty
import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource

data class PosSalesCardTransactionEventDto(
    @JsonProperty("번호") val no: Long,
    @JsonProperty("구분") val type: String,
    @JsonProperty("거래일자") val transactionDateYmd: String,
    @JsonProperty("거래시간") val transactionTimeHms: String,
    @JsonProperty("카드사") val cardCompany: String,
    @JsonProperty("제휴카드사") val affiliateCardCompany: String,
    @JsonProperty("카드번호") val cardNumber: String,
    @JsonProperty("승인번호") val approvalNumber: Long,
    @JsonProperty("승인금액") val approvalAmount: String,
    @JsonProperty("할부기간") val installmentMonth: String,
    @JsonProperty("사업자등록번호") val registrationNumber: String
) {
    constructor(entity: PosSalesCardTransaction) : this(
        no = entity.no,
        type = entity.type,
        transactionDateYmd = entity.transactionDateYmd,
        transactionTimeHms = entity.transactionTimeHms,
        cardCompany = entity.cardCompany,
        affiliateCardCompany = entity.affiliateCardCompany,
        cardNumber = entity.cardNumber,
        approvalNumber = entity.approvalNumber,
        approvalAmount = entity.approvalAmount,
        installmentMonth = entity.installmentMonth,
        registrationNumber = entity.registrationNumber
    )

    fun toEntity(dataSource: PosSalesCardTransactionDataSource): PosSalesCardTransaction {
        return PosSalesCardTransaction(
            no = no,
            type = type,
            transactionDateYmd = transactionDateYmd,
            transactionTimeHms = transactionTimeHms,
            cardCompany = cardCompany,
            affiliateCardCompany = affiliateCardCompany,
            cardNumber = cardNumber,
            approvalNumber = approvalNumber,
            approvalAmount = approvalAmount,
            installmentMonth = installmentMonth,
            registrationNumber = registrationNumber,
            dataSource = dataSource
        )
    }
}