package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PosEasyConnectionAgreementRecordCommandService(
    private val posEasyConnectionAgreementRecordQueryService: PosEasyConnectionAgreementRecordQueryService,
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository,
) {
    @Transactional
    fun upsertPosEasyConnectionAgreementRecord(
        request: PosEasyConnectionAgreementRecordRequest,
    ) {
        posEasyConnectionAgreementRecordQueryService.findPosEasyConnectionAgreementRecord(request)
            .let {
                it?.updateIsAgreedYn(request) ?: posEasyConnectionAgreementRecordJpaRepository.save(request.toEntity())
            }
    }
}