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

@Entity
@Table(name = "pos_sales_card_transactions")
class PosSalesCardTransaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    @Column(nullable = false) val no: Long,
    @Column(nullable = false) val type: String,
    @Column(nullable = false) val transactionDateYmd: String,
    @Column(nullable = false) val transactionTimeHms: String,
    @Column(nullable = false) val cardCompany: String,
    @Column(nullable = false) val affiliateCardCompany: String,
    @Column(nullable = false) val cardNumber: String,
    @Column(nullable = false) val approvalNumber: Long,
    @Column(nullable = false) val approvalAmount: String,
    @Column(nullable = false) val installmentMonth: String,
    @Column(nullable = false) val registrationNumber: String,
    @Column(nullable = false) @Enumerated(EnumType.STRING) val dataSource: PosSalesCardTransactionDataSource
) : BaseTimeEntity() {

}

enum class PosSalesCardTransactionDataSource {
    COMMUNITY,
    CACHE_NOTE
}
