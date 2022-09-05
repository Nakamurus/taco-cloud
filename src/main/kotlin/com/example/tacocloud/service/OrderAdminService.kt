package com.example.tacocloud.service

import com.example.tacocloud.repository.OrderRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class OrderAdminService(
    private val orderRepository: OrderRepository
) {

    @PreAuthorize("hasRole('ADMIN')")
    fun deleteAllOrders() {
        orderRepository.deleteAll()
    }
}