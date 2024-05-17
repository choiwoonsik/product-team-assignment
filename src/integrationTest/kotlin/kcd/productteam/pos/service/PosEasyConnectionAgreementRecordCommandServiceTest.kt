package kcd.productteam.pos.service

import io.kotest.matchers.shouldBe
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PosEasyConnectionAgreementRecordCommandServiceTest(
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository,
    private val sut: PosEasyConnectionAgreementRecordCommandService,
) {
    @BeforeEach
    fun clear() {
        posEasyConnectionAgreementRecordJpaRepository.deleteAllInBatch()
    }

    @Test
    fun `포스 간편 연결 동의 내역 저장`() {
        // given
        val request = PosEasyConnectionAgreementRecordRequest(
            registrationNumber = "registrationNumber",
            agreementType = PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        // when
        sut.createPosEasyConnectionAgreementRecord(request)

        // then
        posEasyConnectionAgreementRecordJpaRepository.findAll().first().apply {
            registrationNumber shouldBe "registrationNumber"
            agreementType shouldBe PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
            isAgreedYn shouldBe "Y"
        }
    }
}