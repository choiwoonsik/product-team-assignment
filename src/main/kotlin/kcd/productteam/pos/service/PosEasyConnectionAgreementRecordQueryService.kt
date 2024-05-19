package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordRepository
import org.springframework.stereotype.Service

@Service
class PosEasyConnectionAgreementRecordQueryService(
    private val posEasyConnectionAgreementRecordRepository: PosEasyConnectionAgreementRecordRepository,
) {
    fun findPosEasyConnectionAgreementRecordByRequest(
        request: PosEasyConnectionAgreementRecordRequest,
    ): PosEasyConnectionAgreementRecord? {
        return posEasyConnectionAgreementRecordRepository.findPosEasyConnectionAgreementRecord(
            request.registrationNumber,
            request.agreementType
        )
    }
}