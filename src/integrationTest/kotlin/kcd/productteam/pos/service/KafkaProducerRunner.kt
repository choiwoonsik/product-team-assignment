package kcd.productteam.pos.service

import kcd.productteam.config.KafkaTopics
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
@Profile("test")
class KafkaProducerRunner(private val kafkaProducerService: KafkaProducerService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        var count = 1
        CompletableFuture.runAsync {
            while (true) {
                kafkaProducerService.sendMessage(KafkaTopics.COMMUNITY_CARD_TRANSACTION, getCardTransactionMessage(count++))
                Thread.sleep(1000)
            }
        }
    }

    private fun getCardTransactionMessage(count: Int): String {
        return """{
            "번호": $count,
            "구분": "승인",
            "거래일자": "2022-02-05",
            "거래시간": "13:45:27",
            "카드사": "농협NH카드",
            "제휴카드사": "농협NH카드",
            "카드번호": "5461-1100-1189-2376",
            "승인번호": 42580512,
            "승인금액": "17,500",
            "할부기간": "일시불",
            "사업자등록번호": "1234567890$count"
        }""".trimIndent()
    }
}
