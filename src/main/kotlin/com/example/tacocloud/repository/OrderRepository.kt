package com.example.tacocloud.repository

import com.example.tacocloud.model.TacoOrder

interface OrderRepository {

    fun save(order: TacoOrder): TacoOrder

    fun findById(id: Long): TacoOrder?
}