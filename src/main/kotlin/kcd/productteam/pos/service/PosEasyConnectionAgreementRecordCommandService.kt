package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PosEasyConnectionAgreementRecordCommandService(
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository,
) {
    @Transactional
    fun createPosEasyConnectionAgreementRecord(
        posEasyConnectionAgreementRecordRequest: PosEasyConnectionAgreementRecordRequest,
    ) {
        posEasyConnectionAgreementRecordJpaRepository.save(posEasyConnectionAgreementRecordRequest.toEntity())
    }
}