package com.example.tacocloud.repository

import com.example.tacocloud.model.TacoOrder
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<TacoOrder, Long> {

    fun save(order: TacoOrder): TacoOrder

}