package kcd.productteam.config

import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        val manager = PoolingHttpClientConnectionManager()
            .apply {
                maxTotal = 100
                defaultMaxPerRoute = 20
            }

        val httpClient = HttpClients.custom()
            .setConnectionManager(manager)
            .build()

        val factory = HttpComponentsClientHttpRequestFactory(httpClient)
            .apply {
                setConnectTimeout(Duration.ofSeconds(3))
                setConnectionRequestTimeout(Duration.ofSeconds(3))
            }

        return RestTemplate(factory)
    }
}