package kcd.productteam.pos.service

import kcd.productteam.external.community.service.CommunityPosService
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_CONNECT
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_VERIFY_CONNECTABLE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PosEasyConnectionAgreementRecordService(
    private val poseEasyConnectionAgreementRecordCommandService: PosEasyConnectionAgreementRecordCommandService,
    private val communityPosService: CommunityPosService,
) {
    @Transactional
    fun createPosEasyConnectionAgreementRecord(request: PosEasyConnectionAgreementRecordRequest) {
        validate(request)
        poseEasyConnectionAgreementRecordCommandService.upsertPosEasyConnectionAgreementRecord(request)

        when (request.agreementType) {
            POS_VERIFY_CONNECTABLE -> communityCheckIsBusinessUser(request)
            POS_CONNECT -> communityRegistPosConnect(request)
        }
    }

    private fun validate(request: PosEasyConnectionAgreementRecordRequest) {
        check(request.isAgreedYn()) { "isAgreedYn 값이 잘못되었습니다." }
        check(request.isValidRegistrationNumber()) { "registrationNumber 값이 잘못되었습니다." }
    }

    private fun communityCheckIsBusinessUser(request: PosEasyConnectionAgreementRecordRequest) {
        if (request.isAgreed()) {
            val isBusinessUser = communityPosService.checkIsBusinessUser(request.registrationNumber)
            if (isBusinessUser.not()) throw IllegalArgumentException("POS 간편 연결 대상이 아닙니다.")
        } else throw IllegalArgumentException("POS 간편 연결 대상 확인 약관에 동의하지 않았습니다.")
    }

    private fun communityRegistPosConnect(request: PosEasyConnectionAgreementRecordRequest) {
        if (request.isAgreed())
            communityPosService.registerDataCommunication(request.toPosEasyConnectionAgreementResult())
        else throw IllegalArgumentException("POS 간편 연결 약관에 동의하지 않았습니다.")
    }
}