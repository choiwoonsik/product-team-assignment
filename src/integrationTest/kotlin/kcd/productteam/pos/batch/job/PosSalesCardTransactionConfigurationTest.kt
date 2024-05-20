package kcd.productteam.pos.batch.job

import io.kotest.matchers.shouldBe
import kcd.productteam.pos.model.PosEasyConnectionAgreementRecord
import kcd.productteam.pos.model.PosEasyConnectionAgreementType
import kcd.productteam.pos.model.PosEasyConnectionAgreementType.POS_CONNECT
import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordRepository
import kcd.productteam.pos.repository.PosSalesCardTransactionRepository
import kcd.productteam.utils.getNowUTCOffsetDateTime
import kcd.productteam.utils.getYmd
import kcd.productteam.utils.toExactlyHour
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = ["spring.batch.job.names=posSalesCardTransactionJob"])
class PosSalesCardTransactionConfigurationTest(
    private val posEasyConnectionAgreementRecordRepository: PosEasyConnectionAgreementRecordRepository,
    private val posSalesCardTransactionRepository: PosSalesCardTransactionRepository,
) {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @BeforeEach
    @Transactional
    fun setUp() {
        posEasyConnectionAgreementRecordRepository.deleteAllInBatch()
        posSalesCardTransactionRepository.deleteAllInBatch()

        val records = (1..100).map { getPosEasyConnectionAgreementRecord(it.toString(), POS_CONNECT, "Y") }
        posEasyConnectionAgreementRecordRepository.saveAll(records)
        val updateRecords = posEasyConnectionAgreementRecordRepository.findAll()
            .map {
                it.dateCreated = getNowUTCOffsetDateTime().toExactlyHour().minusHours(1)
                it
            }
        posEasyConnectionAgreementRecordRepository.saveAll(updateRecords)

        val transactions = (1..100).map { getPosSalesCardTransaction(it.toString()) }
        posSalesCardTransactionRepository.saveAll(transactions)
    }

    @Test
    fun run() {
        val jobParameters = JobParametersBuilder()
            .addString("version", System.currentTimeMillis().toString())
            .toJobParameters()

        val jobExecution: JobExecution = jobLauncherTestUtils.launchJob(jobParameters)
        jobExecution.status shouldBe BatchStatus.COMPLETED
    }

    private fun getPosEasyConnectionAgreementRecord(
        registrationNumber: String,
        agreementType: PosEasyConnectionAgreementType,
        isAgreedYn: String
    ): PosEasyConnectionAgreementRecord {
        return PosEasyConnectionAgreementRecord(
            registrationNumber = registrationNumber,
            agreementType = agreementType,
            isAgreedYn = isAgreedYn,
        )
    }

    private fun getPosSalesCardTransaction(registrationNumber: String): PosSalesCardTransaction {
        return PosSalesCardTransaction(
            no = 1,
            type = "승인",
            transactionDateYmd = getNowUTCOffsetDateTime().minusDays(1).getYmd(),
            transactionTimeHms = "13:45:27",
            cardCompany = "농협NH카드",
            affiliateCardCompany = "농협NH카드",
            cardNumber = "5461-1100-1189-2376",
            approvalNumber = 42580512,
            approvalAmount = "17,500",
            installmentMonth = "일시불",
            registrationNumber = registrationNumber,
            dataSource = PosSalesCardTransactionDataSource.COMMUNITY
        )
    }
}