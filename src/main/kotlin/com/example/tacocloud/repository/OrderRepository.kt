package com.example.tacocloud.repository

import com.example.tacocloud.model.TacoOrder
import com.example.tacocloud.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<TacoOrder, Long> {
    fun save(order: TacoOrder): TacoOrder

    fun findByUserOrderByPlacedAtDesc(
        user: User,
        pageable: Pageable
    ): List<TacoOrder>
}