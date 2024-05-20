package kcd.productteam.pos.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource
import kcd.productteam.pos.model.QPosSalesCardTransaction.Companion.posSalesCardTransaction
import kcd.productteam.utils.getNowUTCOffsetDateTime
import kcd.productteam.utils.getYmd
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PosSalesCardTransactionRepository(
    private val posSalesCardTransactionJpaRepository: PosSalesCardTransactionJpaRepository
) : QuerydslRepositorySupport(PosSalesCardTransaction::class.java),
    PosSalesCardTransactionJpaRepository by posSalesCardTransactionJpaRepository {

    @PersistenceContext
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }

    fun findPosSalesCardTransactionDaily(
        registrationNumbers: List<String>,
        dataSource: PosSalesCardTransactionDataSource,
    ): List<PosSalesCardTransaction> {
        return from(posSalesCardTransaction)
            .where(posSalesCardTransaction.registrationNumber.`in`(registrationNumbers))
            .where(posSalesCardTransaction.dataSource.eq(dataSource))
            .where(posSalesCardTransaction.transactionDateYmd.eq(getNowUTCOffsetDateTime().minusDays(1).getYmd()))
            .fetch()
    }

    fun findPosSalesCardTransactionPastMonthHistory(
        registrationNumber: String,
        dataSource: PosSalesCardTransactionDataSource,
    ): List<PosSalesCardTransaction> {
        return from(posSalesCardTransaction)
            .where(posSalesCardTransaction.registrationNumber.eq(registrationNumber))
            .where(posSalesCardTransaction.dataSource.eq(dataSource))
            .where(
                posSalesCardTransaction.transactionDateYmd.between(
                    getNowUTCOffsetDateTime().minusMonths(6).withDayOfMonth(1).getYmd(),
                    getNowUTCOffsetDateTime().minusDays(1).getYmd()
                )
            )
            .fetch()
    }
}