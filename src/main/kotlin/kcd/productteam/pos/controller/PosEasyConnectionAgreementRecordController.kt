package kcd.productteam.pos.controller

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.service.PosEasyConnectionAgreementRecordService
import kcd.productteam.utils.CommonResponse
import kcd.productteam.utils.getFailureResult
import kcd.productteam.utils.getSuccessResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/pos/easy-connection")
class PosEasyConnectionAgreementRecordController(
    private val posEasyConnectionAgreementRecordService: PosEasyConnectionAgreementRecordService,
) {

    @PostMapping("/agreements/verify-connectable")
    fun posEasyConnectionVerifyConnectableAgree(
        @RequestBody(required = true) request: PosEasyConnectionAgreementRecordRequest,
    ): CommonResponse {
        if (request.agreementType != PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE) {
            return getFailureResult("잘못된 요청입니다.")
        }

        posEasyConnectionAgreementRecordService.createPosEasyConnectionAgreementRecord(request)

        return getSuccessResult()
    }

    @PostMapping("/agreements/connect")
    fun posEasyConnectionConnectAgree(
        @RequestBody(required = true) request: PosEasyConnectionAgreementRecordRequest,
    ): CommonResponse {
        if (request.agreementType != PosEasyConnectionAgreementType.POS_CONNECT) {
            return getFailureResult("잘못된 요청입니다.")
        }

        posEasyConnectionAgreementRecordService.createPosEasyConnectionAgreementRecord(request)

        return getSuccessResult()
    }
}