package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest
import org.springframework.stereotype.Service

@Service
class PosEasyConnectionAgreementRecordService(
    private val poseEasyConnectionAgreementRecordCommandService: PosEasyConnectionAgreementRecordCommandService,
) {
    fun createPosEasyConnectionAgreementRecord(request: PosEasyConnectionAgreementRecordRequest) {
        validate(request)
        poseEasyConnectionAgreementRecordCommandService.upsertPosEasyConnectionAgreementRecord(request)

        if (request.isAgreed()) Unit // TODO: 공동체 검증 로직 추가
        else throw IllegalArgumentException("POS 간편 연결 대상 확인 약관에 동의하지 않았습니다.")
    }

    private fun validate(request: PosEasyConnectionAgreementRecordRequest) {
        check(request.isAgreedYn()) { "isAgreedYn 값이 잘못되었습니다." }
        check(request.isValidRegistrationNumber()) { "registrationNumber 값이 잘못되었습니다." }
    }
}