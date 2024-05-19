package kcd.productteam.pos.service

import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
import kcd.productteam.pos.model.PosSalesCardTransactionDataSource.COMMUNITY
import kcd.productteam.pos.repository.PosSalesCardTransactionJpaRepository
import org.springframework.stereotype.Service

@Service
class PosSalesCardTransactionConsumerService(
    private val posSalesCardTransactionJpaRepository: PosSalesCardTransactionJpaRepository,
) {

    fun savePosSalesCardTransactionFromCommunity(eventList: List<PosSalesCardTransactionEventDto>) {
        eventList
            .chunked(50)
            .forEach { chunked ->
                chunked
                    .map { it.toEntity(COMMUNITY) }
                    .let { posSalesCardTransactionJpaRepository.saveAll(it) }
            }
    }
}