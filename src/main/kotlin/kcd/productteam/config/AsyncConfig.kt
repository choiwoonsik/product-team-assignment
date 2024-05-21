package kcd.productteam.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
@Configuration
class AsyncConfig {

    @Bean("posSalesCardTransactionAsyncExecutor")
    fun posSalesCardTransactionAsyncExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.queueCapacity = 10
        executor.maxPoolSize = 20
        executor.setThreadNamePrefix("PosSalesCardTransactionAsyncExecutor-")
        executor.initialize()
        return executor
    }
}
