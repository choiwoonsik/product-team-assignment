package kcd.productteam.external.community.service

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kcd.productteam.external.community.api.CommunityApiService
import kcd.productteam.external.community.dto.PosEasyConnectionAgreementResult
import org.junit.jupiter.api.Test

class CommunityPosServiceTest {
    private val communityApiService: CommunityApiService = mockk(relaxed = true)

    private val sut = CommunityPosService(communityApiService)


    @Test
    fun `공동체 간편연결 가능 여부 API`() {
        // given
        every { communityApiService.checkHasBusiness(any()) } returns true

        // when
        val isCommunityUser = sut.checkIsCommunityUser("1234567890")

        // then
        isCommunityUser shouldBe true
    }

    @Test
    fun `약관 동의 내역 저장 API`() {
        // given
        val posEasyConnectionAgreementResult = PosEasyConnectionAgreementResult(
            registrationNumber = "1234567890",
            agreedType = "Y",
        )
        every {
            communityApiService.registerDataCommunication(posEasyConnectionAgreementResult)
        } returns Unit

        // when && then
        sut.registerDataCommunication(posEasyConnectionAgreementResult)
    }
}