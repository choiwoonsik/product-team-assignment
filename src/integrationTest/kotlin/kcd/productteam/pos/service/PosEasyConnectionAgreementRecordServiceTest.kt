package kcd.productteam.pos.service

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kcd.productteam.pos.PosTestFixtures
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_CONNECT
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PosEasyConnectionAgreementRecordServiceTest(
    private val posEasyConnectionAgreementRecordService: PosEasyConnectionAgreementRecordService,
    private val posEasyConnectionAgreementRecordQueryService: PosEasyConnectionAgreementRecordQueryService,
) {
    private val posTestFixtures = PosTestFixtures()

    @Test
    fun `POS 간편 연결 동의 결과 저장 및 공동체 검증 - POS 연동 검증 동의`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            registrationNumber = "0123456789",
            agreementType = POS_VERIFY_CONNECTABLE,
            isAgreedYn = "Y"
        )

        // when
        posEasyConnectionAgreementRecordService.createPosEasyConnectionAgreementRecord(request)

        // then
        val record = posEasyConnectionAgreementRecordQueryService.findPosEasyConnectionAgreementRecordByRequest(request)

        record shouldNotBe null
        record!!.registrationNumber shouldBe request.registrationNumber
        record.agreementType shouldBe request.agreementType
        record.isAgreedYn shouldBe "Y"
    }

    @Test
    fun `POS 간편 연결 동의 결과 저장 및 공동체 검증 - POS 연동 동의`() {
        // given
        val request = posTestFixtures.getPosEasyConnectionAgreementRecordRequest(
            registrationNumber = "0123456789",
            agreementType = POS_CONNECT,
            isAgreedYn = "Y"
        )

        // when
        posEasyConnectionAgreementRecordService.createPosEasyConnectionAgreementRecord(request)

        // then
        val record = posEasyConnectionAgreementRecordQueryService.findPosEasyConnectionAgreementRecordByRequest(request)

        record shouldNotBe null
        record!!.registrationNumber shouldBe request.registrationNumber
        record.agreementType shouldBe request.agreementType
        record.isAgreedYn shouldBe "Y"
    }
}