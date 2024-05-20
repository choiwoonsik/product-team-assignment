package kcd.productteam.pos.batch.job

import kcd.productteam.pos.model.PosSalesCardTransaction
import kcd.productteam.pos.repository.PosEasyConnectionAgreementRecordRepository
import kcd.productteam.pos.repository.PosSalesCardTransactionRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ConditionalOnProperty(name = ["spring.batch.job.names"], havingValue = "posSalesCardTransactionJob")
class PosSalesCardTransactionConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val posEasyConnectionAgreementRecordRepository: PosEasyConnectionAgreementRecordRepository,
    private val posSalesCardTransactionRepository: PosSalesCardTransactionRepository,
) {
    companion object {
        val registrationNumberTargetList: MutableList<String> = mutableListOf()
    }

    @Bean
    fun posSalesCardTransactionJob(): Job {
        return JobBuilder("posSalesCardTransactionJob", jobRepository)
            .start(posSalesCardTransactionRegistrationNumberTarget())
            .next(posSalesCardTransactionStep())
            .build()
    }

    @JobScope
    fun posSalesCardTransactionRegistrationNumberTarget(): Step {
        return StepBuilder("posSalesCardTransactionRegistrationNumberTarget", jobRepository)
            .tasklet(
                { _, _ ->
                    posEasyConnectionAgreementRecordRepository.findAllPosEasyConnectionAgreedRecordDailyTarget()
                        .map { it.registrationNumber }
                        .distinct()
                        .let { registrationNumberTargetList.addAll(it) }
                    RepeatStatus.FINISHED
                },
                transactionManager
            ).build()
    }

    @JobScope
    fun posSalesCardTransactionStep(): Step {
        val reader = PosSalesCardTransactionReader(posSalesCardTransactionRepository)
        val writer = PosSalesCardTransactionWriter()

        return StepBuilder("posSalesCardTransactionStep", jobRepository)
            .chunk<List<PosSalesCardTransaction>, List<PosSalesCardTransaction>>(10, transactionManager)
            .reader(reader)
            .writer(writer)
            .build()
    }
}