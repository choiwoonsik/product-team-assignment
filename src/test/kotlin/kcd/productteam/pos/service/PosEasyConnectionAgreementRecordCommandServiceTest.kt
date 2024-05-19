package kcd.productteam.pos.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kcd.productteam.TestDatabaseSupport
import kcd.productteam.pos.PosTestFixtures
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordJpaRepository
import org.junit.jupiter.api.Test

class PosEasyConnectionAgreementRecordCommandServiceTest : TestDatabaseSupport() {
    private val posEasyConnectionAgreementRecordJpaRepository: PosEasyConnectionAgreementRecordJpaRepository =
        mockk(relaxed = true)
    private val posEasyConnectionAgreementRecordQueryService: PosEasyConnectionAgreementRecordQueryService =
        mockk(relaxed = true)

    private val sut = PosEasyConnectionAgreementRecordCommandService(
        posEasyConnectionAgreementRecordQueryService,
        posEasyConnectionAgreementRecordJpaRepository
    )

    private val posTestFixtures = PosTestFixtures()

    @Test
    fun `포스 간편 연결 동의 내역 저장`() {
        // given
        val request = PosEasyConnectionAgreementRecordRequest(
            registrationNumber = "0123456789",
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        every { posEasyConnectionAgreementRecordQueryService.findPosEasyConnectionAgreementRecordByRequest(request) } returns null
        every { posEasyConnectionAgreementRecordJpaRepository.save(any()) } returns mockk()

        // when
        sut.upsertPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) {
            posEasyConnectionAgreementRecordJpaRepository.save(any())
        }
    }

    @Test
    fun `포스 간편 연결 동의 내역 업데이트`() {
        // given
        val request = PosEasyConnectionAgreementRecordRequest(
            registrationNumber = "0123456789",
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "N"
        )

        val record = mockk<PosEasyConnectionAgreementRecord> {
            posTestFixtures.getPosEasyConnectionAgreementRecord(
                "0123456789",
                POS_VERIFY_CONNECTABLE,
                "Y"
            )
        }

        every { posEasyConnectionAgreementRecordQueryService.findPosEasyConnectionAgreementRecordByRequest(request) } returns record
        every { record.updateIsAgreedYn(request) } returns record
        every { posEasyConnectionAgreementRecordJpaRepository.save(any()) } returns mockk()

        // when
        sut.upsertPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) { record.updateIsAgreedYn(request) }
    }
}