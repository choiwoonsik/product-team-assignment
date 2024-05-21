package kcd.productteam.external.service.restTemplateService

import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RestTemplateService(
    private val restTemplate: RestTemplate,
    private val gson: Gson,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)

    @Retryable(
        maxAttempts = 3,
        backoff = Backoff(delay = 100),
        include = [RuntimeException::class],
    )
    fun <T> fetch(
        method: HttpMethod,
        path: String,
        requestEntity: RequestEntity<*>?,
        queryParams: Map<String, String>,
        responseType: Class<T>,
    ): T {
        logger.info("Fetching data from $path")

        val uri = UriComponentsBuilder.fromHttpUrl(path)
            .apply { queryParams.forEach { (key, value) -> queryParam(key, value) } }
            .build()
            .toUri().toASCIIString()

        val result = restTemplate.exchange(
            uri,
            method,
            requestEntity,
            String::class.java
        )

        if (result.statusCode.is2xxSuccessful) return gson.fromJson(result.body, responseType)
        else throw RuntimeException("Failed to fetch data from $path")
    }
}