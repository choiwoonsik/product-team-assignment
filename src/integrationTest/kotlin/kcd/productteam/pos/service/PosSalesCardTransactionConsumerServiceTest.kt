package kcd.productteam.pos.service

import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import kcd.productteam.pos.repository.PosSalesCardTransactionJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PosSalesCardTransactionConsumerServiceTest(
    private val sut: PosSalesCardTransactionJpaRepository,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.simpleName)

    @BeforeEach
    fun clear() {
        sut.deleteAllInBatch()
    }

    @Test
    fun `POS 카드 매출 데이터를 컨슈밍하여 DB에 적재한다`() {
        // given && when
        log.info("3초 동안 데이터를 적재합니다.")
        Thread.sleep(1000 * 3)
        val all = sut.findAll()
        log.info("DB 에 적재된 카드 매출 데이터 개수: ${all.size}")

        // then
        all.size shouldBeGreaterThanOrEqual 3
    }
}