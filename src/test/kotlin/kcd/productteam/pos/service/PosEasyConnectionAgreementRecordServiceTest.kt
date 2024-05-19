package kcd.productteam.pos.service

import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kcd.productteam.external.community.service.CommunityPosService
import kcd.productteam.pos.PosTestFixtures
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_CONNECT
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PosEasyConnectionAgreementRecordServiceTest {
    private val poseEasyConnectionAgreementRecordCommandService: PosEasyConnectionAgreementRecordCommandService =
        mockk(relaxed = true)
    private val communityPosService: CommunityPosService = mockk(relaxed = true)

    private val sut = PosEasyConnectionAgreementRecordService(
        poseEasyConnectionAgreementRecordCommandService,
        communityPosService
    )

    private val posTestFixtures = PosTestFixtures()

    @Test
    fun `POS 간편 연결 공통 - 동의 여부를 Y,N 이외의 값으로 보내는 경우 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Yn"
        )

        // when && then
        Assertions.assertThrows(IllegalStateException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("isAgreedYn 값이 잘못되었습니다.")
    }

    @Test
    fun `POS 간편 연결 공통 - 사업자 등록 번호가 비어있거나 숫자외 값이 있는 경우 에러를 던진다`() {
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

    @Test
    fun `POS 간편 연결 검증 동의 및 공동체에 검증 요청 진행`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        every { communityPosService.checkIsBusinessUser(request.registrationNumber) } returns true

        // when
        sut.createPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) {
            communityPosService.checkIsBusinessUser(request.registrationNumber)
            poseEasyConnectionAgreementRecordCommandService.upsertPosEasyConnectionAgreementRecord(request)
        }
    }

    @Test
    fun `POS 간편 연결 검증 동의 및 공동체에 검증 요청 진행 - 공동체 검증 결과가 false 인 경우 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        every {
            communityPosService.checkIsBusinessUser(request.registrationNumber)
        } returns false

        // when && then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("POS 간편 연결 대상이 아닙니다.")
    }

    @Test
    fun `POS 간편 연결 검증 동의 및 공동체에 검증 요청 진행 - 공동체 검증을 동의하지 않는다면 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "N"
        )

        every {
            communityPosService.checkIsBusinessUser(request.registrationNumber)
        } returns false

        // when && then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("POS 간편 연결 대상 확인 약관에 동의하지 않았습니다.")
    }

    @Test
    fun `POS 간편 연결 동의 및 공동체에 결과 저장 요청`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_CONNECT,
            isAgreedYn = "Y"
        )

        every {
            communityPosService.registerDataCommunication(request.toPosEasyConnectionAgreementResult())
        } returns Unit

        // when
        sut.createPosEasyConnectionAgreementRecord(request)

        // then
        verify(exactly = 1) {
            communityPosService.registerDataCommunication(request.toPosEasyConnectionAgreementResult())
            poseEasyConnectionAgreementRecordCommandService.upsertPosEasyConnectionAgreementRecord(request)
        }
    }

    @Test
    fun `POS 간편 연결 동의 및 공동체에 결과 저장 요청 - POS 간편 연결을 동의하지 않는다면 에러를 던진다`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            agreementType = POS_CONNECT,
            isAgreedYn = "N"
        )

        every {
            communityPosService.registerDataCommunication(request.toPosEasyConnectionAgreementResult())
        } returns Unit

        // when && then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut.createPosEasyConnectionAgreementRecord(request)
        }.shouldHaveMessage("POS 간편 연결 약관에 동의하지 않았습니다.")
    }
}