package kcd.productteam.pos.dto

import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType

data class PosEasyConnectionAgreementRecordRequest(
    val registrationNumber: String,
    val agreementType: PosEasyConnectionAgreementType,
    val isAgreedYn: String,
) {
    fun toEntity(): PosEasyConnectionAgreementRecord {
        return PosEasyConnectionAgreementRecord(
            registrationNumber = this.registrationNumber,
            agreementType = this.agreementType,
            isAgreedYn = this.isAgreedYn,
        )
    }
}
