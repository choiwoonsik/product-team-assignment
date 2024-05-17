package kcd.productteam.pos.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kcd.productteam.TestDatabaseSupport
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordJpaRepository
import org.junit.jupiter.api.Test

class PosEasyConnectionAgreementRecordCommandServiceTest : TestDatabaseSupport() {
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository =
        mockk(relaxed = true)

    private val sut = PosEasyConnectionAgreementRecordCommandService(
        posEasyConnectionAgreementRecordJpaRepository
    )

    @Test
    fun `포스 간편 연결 동의 내역 저장`() {
        // given
        val request = PosEasyConnectionAgreementRecordRequest(
            registrationNumber = "registrationNumber",
            agreementType = PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        every { posEasyConnectionAgreementRecordJpaRepository.save(any()) } returns mockk()

        // when
        sut.createPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) {
            posEasyConnectionAgreementRecordJpaRepository.save(any())
        }
    }
}