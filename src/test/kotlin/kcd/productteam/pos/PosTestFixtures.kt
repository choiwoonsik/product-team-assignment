package kcd.productteam.pos

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource

class PosTestFixtures {
    fun getPosEasyConnectionAgreementRecord(
        registrationNumber: String,
        agreementType: PosEasyConnectionAgreementType,
        isAgreedYn: String
    ): PosEasyConnectionAgreementRecord {
        return PosEasyConnectionAgreementRecord(
            registrationNumber = registrationNumber,
            agreementType = agreementType,
            isAgreedYn = isAgreedYn
        )
    }

    fun getPosEasyConnectionAgreementRecordRequest(
        registrationNumber: String? = "0123456789",
        agreementType: PosEasyConnectionAgreementType,
        isAgreedYn: String
    ): PosEasyConnectionAgreementRecordRequest {
        return PosEasyConnectionAgreementRecordRequest(
            registrationNumber = registrationNumber!!,
            agreementType = agreementType,
            isAgreedYn = isAgreedYn
        )
    }

    fun getPosSalesCardTransactionEventDto(no: Long): PosSalesCardTransactionEventDto {
        return PosSalesCardTransactionEventDto(
            no = no,
            type = "승인",
            transactionDateYmd = "2022-02-05",
            transactionTimeHms = "13:45:27",
            cardCompany = "농협NH카드",
            affiliateCardCompany = "농협NH카드",
            cardNumber = "5461-11-****-****",
            approvalNumber = 42580512,
            approvalAmount = "17,500",
            installmentMonth = "일시불",
            registrationNumber = "1234567890",
        )
    }

    fun getPosSalesCardTransaction(
        registrationNumber: String,
        transactionDateYmd: String = "2022-02-05",
        transactionTimeHms: String = "13:45:27",
        dataSource: PosSalesCardTransactionDataSource = PosSalesCardTransactionDataSource.COMMUNITY
    ): PosSalesCardTransaction {
        return PosSalesCardTransaction(
            no = 1,
            type = "승인",
            transactionDateYmd = transactionDateYmd,
            transactionTimeHms = transactionTimeHms,
            cardCompany = "농협NH카드",
            affiliateCardCompany = "농협NH카드",
            cardNumber = "5461-11-****-****",
            approvalNumber = 42580512,
            approvalAmount = "17,500",
            installmentMonth = "일시불",
            registrationNumber = registrationNumber,
            dataSource = dataSource,
        )
    }
}