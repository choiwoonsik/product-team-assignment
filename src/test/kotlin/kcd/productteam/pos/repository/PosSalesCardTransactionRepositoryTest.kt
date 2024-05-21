package kcd.productteam.pos.repository

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jakarta.persistence.EntityManager
import kcd.productteam.TestDatabaseSupport
import kcd.productteam.pos.PosTestFixtures
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource.CACHE_NOTE
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class PosSalesCardTransactionRepositoryTest(
    private val posSalesCardTransactionJpaRepository: PosSalesCardTransactionJpaRepository,
    private val entityManager: EntityManager,
) : TestDatabaseSupport() {

    private val sut = PosSalesCardTransactionRepository(posSalesCardTransactionJpaRepository)

    private val postTestFixtures = PosTestFixtures()

    @BeforeEach
    fun init() {
        sut.setEntityManager(entityManager)
    }

    @AfterEach
    fun after() {
        unmockkStatic(OffsetDateTime::class)
    }

    @Test
    fun `POS 카드 매출 거래 데이터 일일 조회 - 전일자 데이터 처리`() {
        // given
        mockkStatic(OffsetDateTime::class)
        every { OffsetDateTime.now(ZoneOffset.UTC) } returns OffsetDateTime.parse("2024-05-02T01:00:00Z")

        posSalesCardTransactionJpaRepository.saveAll(
            listOf(
                postTestFixtures.getPosSalesCardTransaction(
                    "0",
                    transactionDateYmd = "2024-05-01",
                    transactionTimeHms = "00:00:00",
                    dataSource = CACHE_NOTE
                ),
                postTestFixtures.getPosSalesCardTransaction(
                    "1",
                    transactionDateYmd = "2024-05-01",
                    transactionTimeHms = "12:00:00",
                    dataSource = CACHE_NOTE
                ),
                postTestFixtures.getPosSalesCardTransaction(
                    "2",
                    transactionDateYmd = "2024-05-01",
                    transactionTimeHms = "23:59:59",
                    dataSource = CACHE_NOTE
                ),
            )
        )

        // when
        val yesterdayDaily = sut.findAllPosSalesCardTransactionDaily(
            listOf("0", "1", "2"),
            CACHE_NOTE
        )

        // then
        yesterdayDaily.size shouldBe 3
        yesterdayDaily[0].transactionDateYmd shouldBe "2024-05-01"
        yesterdayDaily[1].transactionDateYmd shouldBe "2024-05-01"
        yesterdayDaily[2].transactionDateYmd shouldBe "2024-05-01"
        yesterdayDaily[0].transactionTimeHms shouldBe "00:00:00"
        yesterdayDaily[1].transactionTimeHms shouldBe "12:00:00"
        yesterdayDaily[2].transactionTimeHms shouldBe "23:59:59"
        yesterdayDaily[0].registrationNumber shouldBe "0"
        yesterdayDaily[1].registrationNumber shouldBe "1"
        yesterdayDaily[2].registrationNumber shouldBe "2"
        yesterdayDaily[0].dataSource shouldBe CACHE_NOTE
        yesterdayDaily[1].dataSource shouldBe CACHE_NOTE
        yesterdayDaily[2].dataSource shouldBe CACHE_NOTE
    }

    @Test
    fun `POS 카드 매출 거래 데이터 과거 6개월치 조회`() {
        // given
        mockkStatic(OffsetDateTime::class)
        every { OffsetDateTime.now(ZoneOffset.UTC) } returns OffsetDateTime.parse("2024-07-01T06:00:00Z")

        posSalesCardTransactionJpaRepository.saveAll(
            listOf(
                postTestFixtures.getPosSalesCardTransaction(
                    "0",
                    transactionDateYmd = "2024-01-01",
                    transactionTimeHms = "00:00:00",
                    dataSource = CACHE_NOTE
                ),
                postTestFixtures.getPosSalesCardTransaction(
                    "0",
                    transactionDateYmd = "2024-04-01",
                    transactionTimeHms = "12:00:00",
                    dataSource = CACHE_NOTE
                ),
                postTestFixtures.getPosSalesCardTransaction(
                    "0",
                    transactionDateYmd = "2024-06-30",
                    transactionTimeHms = "23:59:59",
                    dataSource = CACHE_NOTE
                ),
                postTestFixtures.getPosSalesCardTransaction(
                    "0",
                    transactionDateYmd = "2024-07-01",
                    transactionTimeHms = "00:00:00",
                    dataSource = CACHE_NOTE
                ),
            )
        )

        // when
        val sixMonthHistory = sut.findAllPosSalesCardTransactionPastMonthHistory(
            6,
            "0",
            CACHE_NOTE
        )

        // then
        sixMonthHistory.size shouldBe 3
        sixMonthHistory[0].transactionDateYmd shouldBe "2024-01-01"
        sixMonthHistory[1].transactionDateYmd shouldBe "2024-04-01"
        sixMonthHistory[2].transactionDateYmd shouldBe "2024-06-30"
        sixMonthHistory[0].transactionTimeHms shouldBe "00:00:00"
        sixMonthHistory[1].transactionTimeHms shouldBe "12:00:00"
        sixMonthHistory[2].transactionTimeHms shouldBe "23:59:59"
        sixMonthHistory[0].registrationNumber shouldBe "0"
        sixMonthHistory[1].registrationNumber shouldBe "0"
        sixMonthHistory[2].registrationNumber shouldBe "0"
        sixMonthHistory[0].dataSource shouldBe CACHE_NOTE
        sixMonthHistory[1].dataSource shouldBe CACHE_NOTE
        sixMonthHistory[2].dataSource shouldBe CACHE_NOTE
    }
}