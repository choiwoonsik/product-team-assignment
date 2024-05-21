package kcd.productteam.external.community.service

import kcd.productteam.external.community.api.CommunityApiService
import kcd.productteam.external.community.dto.PosEasyConnectionAgreementResult
import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
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

    fun produceSalesCardTransactionList(posSalesCardTransactionEventDtoList: List<PosSalesCardTransactionEventDto>) {
        communityApiService.produceSalesCardTransactionList(posSalesCardTransactionEventDtoList)
    }
}