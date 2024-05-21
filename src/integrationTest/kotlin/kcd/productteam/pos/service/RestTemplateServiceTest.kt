package kcd.productteam.pos.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.verify
import kcd.productteam.external.service.restTemplateService.RestTemplateService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.web.client.RestTemplate

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RestTemplateServiceTest(
    private val sut: RestTemplateService
) {
    @MockkBean(relaxed = true)
    private lateinit var restTemplate: RestTemplate

    @Test
    fun `restTemplate 호출 시 예외가 발생하면 retry 한다`() {
        // given
        val path = "http://localhost:8080/hi"

        every {
            restTemplate.exchange(
                path,
                HttpMethod.GET,
                null,
                String::class.java
            )
        } returns ResponseEntity(HttpStatusCode.valueOf(400))

        // when
        Assertions.assertThrows(RuntimeException::class.java) {
            sut.fetch(HttpMethod.GET, path, null, emptyMap(), String::class.java)
        }.shouldHaveMessage("Failed to fetch data from $path")

        // then
        verify(exactly = 3) {
            restTemplate.exchange(
                path,
                HttpMethod.GET,
                null,
                String::class.java
            )
        }
    }
}