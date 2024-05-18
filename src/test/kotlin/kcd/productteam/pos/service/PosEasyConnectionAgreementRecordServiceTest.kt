package kcd.productteam.pos.service

import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.mockk
import io.mockk.verify
import kcd.productteam.pos.PosTestFixtures
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PosEasyConnectionAgreementRecordServiceTest {
    private val poseEasyConnectionAgreementRecordCommandService: PosEasyConnectionAgreementRecordCommandService =
        mockk(relaxed = true)

    private val sut = PosEasyConnectionAgreementRecordService(
        poseEasyConnectionAgreementRecordCommandService
    )

    private val posTestFixtures = PosTestFixtures()

    @Test
    fun `POS 간편 연결 검증 동의 및 검증 진행`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(agreementType = POS_VERIFY_CONNECTABLE, isAgreedYn = "Y")

        // when
        sut.createPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) {
            poseEasyConnectionAgreementRecordCommandService.upsertPosEasyConnectionAgreementRecord(request)
        }
    }

    @Test
    fun `POS 간편 연결 검증 동의 및 검증 진행 - 동의 여부를 Y,N 이외의 값으로 보내는 경우 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(agreementType = POS_VERIFY_CONNECTABLE, isAgreedYn = "Yn")

        // when && then
        Assertions.assertThrows(IllegalStateException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("isAgreedYn 값이 잘못되었습니다.")
    }

    @Test
    fun `POS 간편 연결 검증 동의 및 검증 진행 - 사업자 등록 번호가 비어있거나 숫자외 값이 있는 경우 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            registrationNumber = "0123456789a",
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        // when && then
        Assertions.assertThrows(IllegalStateException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("registrationNumber 값이 잘못되었습니다.")
    }
}