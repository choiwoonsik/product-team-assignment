package kcd.productteam.pos.batch.job

import kcd.productteam.external.community.service.CommunityPosService
import kcd.productteam.pos.dto.PosSalesCardTransactionEventDto
import kcd.productteam.pos.model.PosSalesCardTransaction
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemStreamWriter
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.CompletableFuture

class PosSalesCardTransactionWriter(
    private val communityPosService: CommunityPosService,
    private val executor: ThreadPoolTaskExecutor,
) : ItemStreamWriter<List<PosSalesCardTransaction>> {

    companion object {
        private const val CHUNK_SIZE = 1000
    }

    override fun write(chunk: Chunk<out List<PosSalesCardTransaction>>) {
        chunk.items
            .flatten()
            .chunked(CHUNK_SIZE)
            .forEach { posSalesCardTransactionChunked ->
                CompletableFuture.runAsync(
                    {
                        posSalesCardTransactionChunked
                            .map { PosSalesCardTransactionEventDto(it) }
                            .let { communityPosService.produceSalesCardTransactionList(it) }
                    },
                    executor
                )
            }
    }
}