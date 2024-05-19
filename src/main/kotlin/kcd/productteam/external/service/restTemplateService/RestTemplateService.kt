package kcd.productteam.external.service.restTemplateService

import com.google.gson.Gson
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

    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 100))
    fun <T> fetch(
        method: HttpMethod,
        path: String,
        requestEntity: RequestEntity<*>?,
        queryParams: Map<String, String>,
        responseType: Class<T>,
    ): T {
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