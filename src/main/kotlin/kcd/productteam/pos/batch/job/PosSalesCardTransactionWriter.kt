package kcd.productteam.pos.batch.job

import kcd.productteam.pos.model.PosSalesCardTransaction
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemStreamWriter

class PosSalesCardTransactionWriter(

) : ItemStreamWriter<List<PosSalesCardTransaction>> {

    override fun write(chunk: Chunk<out List<PosSalesCardTransaction>>) {
        chunk.items.forEach { posSalesCardTransactionList ->
            // TODO: 카프카 프로듀싱
            println(posSalesCardTransactionList)
        }
    }
}