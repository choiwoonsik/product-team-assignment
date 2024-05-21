package kcd.productteam.pos.external.community.api

import io.kotest.matchers.shouldBe
import kcd.productteam.external.community.api.CommunityApiService
import kcd.productteam.external.community.dto.PosEasyConnectionAgreementResult
import kcd.productteam.pos.PosTestFixtures
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CommunityApiServiceTest(
    private val communityApiService: CommunityApiService,
) {
    private val posTestFixtures = PosTestFixtures()

    @Test
    fun `공동체 간편연결 가능 여부 API`() {
        // given
        val registrationNumber = "1234567890"

        // when
        val result = communityApiService.checkHasBusiness(registrationNumber)

        // then
        result shouldBe true
    }

    @Test
    fun `약관 동의 내역 저장 API`() {
        // given
        val posEasyConnectionAgreementResult = PosEasyConnectionAgreementResult(
            registrationNumber = "1234567890",
            agreedType = "Y",
        )

        // when && then
        communityApiService.registerDataCommunication(posEasyConnectionAgreementResult)
    }

    @Test
    fun `POS 카드 매출 데이터 전일자 적재 API`() {
        // given
        val transactionEventDtoList = listOf(
            posTestFixtures.getPosSalesCardTransactionEventDto(0),
            posTestFixtures.getPosSalesCardTransactionEventDto(1),
            posTestFixtures.getPosSalesCardTransactionEventDto(2)
        )

        // when && then
        communityApiService.produceSalesCardTransactionList(transactionEventDtoList)
    }
}