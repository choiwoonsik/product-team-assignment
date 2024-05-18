package kcd.productteam.pos

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType

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
}