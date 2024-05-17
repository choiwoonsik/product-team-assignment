package kcd.productteam.pos.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PosEasyConnectionAgreementRecordQueryRepositoryTest(
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository,
    private val sut: PosEasyConnectionAgreementRecordQueryRepository,
) {
    @BeforeEach
    fun clear() {
        posEasyConnectionAgreementRecordJpaRepository.deleteAllInBatch()
    }

    @Test
    fun `포스 간편 연결 동의 내역 조회`() {
        // given
        val request = PosEasyConnectionAgreementRecordRequest(
            registrationNumber = "registrationNumber",
            agreementType = PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )
        posEasyConnectionAgreementRecordJpaRepository.save(request.toEntity())

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