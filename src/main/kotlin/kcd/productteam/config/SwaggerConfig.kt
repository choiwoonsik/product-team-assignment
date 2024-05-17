package kcd.productteam.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun productTeamApi(): OpenAPI {
        val productTeamInfo = Info()
            .title("KCD 상품팀 API 명세서")
        val pricingNotion = ExternalDocumentation()
            .description("KCD 상품팀 POS 데이터 연동 설계 문서")
            .url("https://www.notion.so/woonsik/KCD-ac9fc94904b74c518d69ee30028ca808")
        return OpenAPI()
            .info(productTeamInfo)
            .externalDocs(pricingNotion)
    }
}