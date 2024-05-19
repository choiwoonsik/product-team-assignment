package kcd.productteam.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import java.io.Serializable

@Configuration
@EnableKafka
class KafkaConfig {

    @Value("\${kafka.endpoint.community}")
    private var communityKafkaBrokers: String? = null

    @Value("\${kafka.consumer.group.id}")
    private var consumerGroupId: String? = null

    @Bean
    fun communityKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory(communityKafkaBrokers!!, consumerGroupId!!)
        factory.isBatchListener = true
        factory.containerProperties.pollTimeout = 60_000
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE

        return factory
    }

    private fun consumerFactory(
        brokers: String,
        consumerGroupId: String,
    ): ConsumerFactory<String, String> {
        val configProps = consumerProperties(brokers, consumerGroupId)
        return DefaultKafkaConsumerFactory(configProps)
    }

    private fun consumerProperties(brokers: String, consumerGroupId: String): Map<String, Serializable> {
        val configProps = hashMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers,
            ConsumerConfig.GROUP_ID_CONFIG to consumerGroupId,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        )

        return configProps
    }
}
