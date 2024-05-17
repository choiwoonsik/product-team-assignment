package kcd.productteam.pos.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import kcd.productteam.TestDatabaseSupport
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PosEasyConnectionAgreementRecordQueryRepositoryTest(
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository,
    private val entityManager: EntityManager,
) : TestDatabaseSupport() {
    private val sut = PosEasyConnectionAgreementRecordQueryRepository(
        posEasyConnectionAgreementRecordJpaRepository
    )

    @BeforeEach
    fun init() {
        sut.setEntityManager(entityManager)
    }

    @Test
    fun `포스 간편 연결 동의 내역 조회`() {
        // given
        sut.save(
            PosEasyConnectionAgreementRecord(
                registrationNumber = "registrationNumber",
                agreementType = PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE,
                isAgreedYn = "Y"
            )
        )


        // when
        val result = sut.findPosEasyConnectionAgreementRecord(
            "registrationNumber",
            PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
        )

        // then
        result shouldNotBe null
        result!!.registrationNumber shouldBe "registrationNumber"
        result.agreementType shouldBe PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
        result.isAgreedYn shouldBe "Y"
    }

}