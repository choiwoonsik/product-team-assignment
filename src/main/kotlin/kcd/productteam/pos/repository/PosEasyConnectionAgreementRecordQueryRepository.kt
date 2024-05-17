package kcd.productteam.pos.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.model.QPosEasyConnectionAgreementRecord.Companion.posEasyConnectionAgreementRecord
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PosEasyConnectionAgreementRecordQueryRepository(
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository
) : QuerydslRepositorySupport(PosEasyConnectionAgreementRecord::class.java),
    PosEasyConnectionAgreementRecordJpaRepository by posEasyConnectionAgreementRecordJpaRepository {

    @PersistenceContext
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }

    fun findPosEasyConnectionAgreementRecord(
        registrationNumber: String,
        agreementType: PosEasyConnectionAgreementType,
    ): PosEasyConnectionAgreementRecord? {
        return from(posEasyConnectionAgreementRecord)
            .where(posEasyConnectionAgreementRecord.registrationNumber.eq(registrationNumber))
            .where(posEasyConnectionAgreementRecord.agreementType.eq(agreementType))
            .fetchOne()
    }
}