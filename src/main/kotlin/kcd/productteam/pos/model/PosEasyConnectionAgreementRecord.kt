package kcd.productteam.pos.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kcd.productteam.BaseTimeEntity
import kcd.productteam.pos.dto.PosEasyConnectionAgreementRecordRequest

@Entity
@Table(name = "pos_easy_connection_agreement_records")
class PosEasyConnectionAgreementRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
    @Column(nullable = false) val registrationNumber: String,
    @Column(nullable = false) @Enumerated(EnumType.STRING) val agreementType: PosEasyConnectionAgreementType,
    @Column(nullable = false) val isAgreedYn: String,
) : BaseTimeEntity() {

    companion object {
        fun from(request: PosEasyConnectionAgreementRecordRequest): PosEasyConnectionAgreementRecord {
            return PosEasyConnectionAgreementRecord(
                registrationNumber = request.registrationNumber,
                agreementType = request.agreementType,
                isAgreedYn = request.isAgreedYn,
            )
        }
    }
}

enum class PosEasyConnectionAgreementType {
    POS_VERIFY_CONNECTABLE,
    POS_CONNECT,
}