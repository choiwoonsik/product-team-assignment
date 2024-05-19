package kcd.productteam.pos.listener

import kcd.productteam.config.KafkaTopics
import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
import kcd.productteam.pos.service.PosSalesCardTransactionConsumerService
import kcd.productteam.utils.toJsonObject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class PosSalesCardTransactionConsumer(
    private val posSalesCardTransactionConsumerService: PosSalesCardTransactionConsumerService,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.simpleName)

    @KafkaListener(
        id = KafkaTopics.COMMUNITY_CARD_TRANSACTION,
        topics = [KafkaTopics.COMMUNITY_CARD_TRANSACTION],
        idIsGroup = false,
        concurrency = "1",
        containerFactory = "communityKafkaListenerContainerFactory",
    )
    fun listen(
        records: List<ConsumerRecord<String, String>>,
        acknowledgement: Acknowledgment,
    ) {
        val events = records.map { record ->
            val data = record.value()
            log.info("Received Message - topic = ${record.topic()}, value = $data")
            data.toJsonObject(PosSalesCardTransactionEventDto::class.java)
        }

        posSalesCardTransactionConsumerService.savePosSalesCardTransactionFromCommunity(events)

        acknowledgement.acknowledge()
    }
}