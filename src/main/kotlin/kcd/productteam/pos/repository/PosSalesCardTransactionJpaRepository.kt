package kcd.productteam.pos.repository

import kcd.productteam.pos.model.PosSalesCardTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface PosSalesCardTransactionJpaRepository: JpaRepository<PosSalesCardTransaction, Long>