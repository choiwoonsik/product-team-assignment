package kcd.productteam.external.community.service

import kcd.productteam.external.community.api.CommunityApiService
import kcd.productteam.external.community.dto.PosEasyConnectionAgreementResult
import org.springframework.stereotype.Service

@Service
class CommunityPosService(
    private val communityApiService: CommunityApiService,
) {

    fun checkIsBusinessUser(registrationNumber: String): Boolean {
        return communityApiService.checkHasBusiness(registrationNumber)
    }

    fun registerDataCommunication(posEasyConnectionAgreementResult: PosEasyConnectionAgreementResult) {
        communityApiService.registerDataCommunication(posEasyConnectionAgreementResult)
    }
}