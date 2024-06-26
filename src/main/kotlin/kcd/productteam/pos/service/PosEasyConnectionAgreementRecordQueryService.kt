package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PosEasyConnectionAgreementRecordQueryService(
    private val posEasyConnectionAgreementRecordRepository: PosEasyConnectionAgreementRecordRepository,
) {
    @Transactional(readOnly = true)
    fun findPosEasyConnectionAgreementRecordByRequest(
        request: PosEasyConnectionAgreementRecordRequest,
    ): PosEasyConnectionAgreementRecord? {
        return posEasyConnectionAgreementRecordRepository.findPosEasyConnectionAgreementRecord(
            request.registrationNumber,
            request.agreementType
        )
    }
}