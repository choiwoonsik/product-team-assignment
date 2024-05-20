package kcd.productteam.pos.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_CONNECT
import kcd.productteam.pos.model.QPosEasyConnectionAgreementRecord.Companion.posEasyConnectionAgreementRecord
import kcd.productteam.utils.getNowUTCOffsetDateTime
import kcd.productteam.utils.toExactlyHour
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PosEasyConnectionAgreementRecordRepository(
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

    fun findAllPosEasyConnectionAgreedRecordDailyTarget(
    ): List<PosEasyConnectionAgreementRecord> {
        return from(posEasyConnectionAgreementRecord)
            .where(posEasyConnectionAgreementRecord.agreementType.eq(POS_CONNECT))
            .where(posEasyConnectionAgreementRecord.isAgreedYn.eq("Y"))
            .where(
                posEasyConnectionAgreementRecord.dateCreated.loe(
                    getNowUTCOffsetDateTime().toExactlyHour().minusNanos(1)
                )
            )
            .fetch()
    }
}