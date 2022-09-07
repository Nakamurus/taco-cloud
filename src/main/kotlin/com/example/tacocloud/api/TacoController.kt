package com.example.tacocloud.api

import com.example.tacocloud.model.Taco
import com.example.tacocloud.model.TacoOrder
import com.example.tacocloud.repository.OrderRepository
import com.example.tacocloud.repository.TacoRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.full.memberProperties

@RestController
@RequestMapping(
    path=["/api/tacos"],
    produces = ["application/json"]
)
@CrossOrigin(origins = ["http://tacocloud:8080"])
class TacoController(
    private val tacoRepository: TacoRepository,
    private val orderRepository: OrderRepository
) {
    @GetMapping(params = ["recent"])
    fun recentTacos(): Iterable<Taco> {
        val page: PageRequest = PageRequest.of(0, 12,
            Sort.by("createdAt").descending()
        )
        return tacoRepository.findAll(page).content
    }

    @GetMapping("/{id}")
    fun tacoById(@PathVariable("id") id: Long): ResponseEntity<Taco> {
        return tacoRepository.findById(id).orElse(null)
            ?.let { ResponseEntity(it, HttpStatus.OK) } ?:
            ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postTaco(@RequestBody taco: Taco): Taco {
        return tacoRepository.save(taco)
    }

    @PatchMapping(path = ["/{orderId}"],
        consumes = ["application/json"])
    fun patchOrder(
        @PathVariable("orderId") orderId: Long,
        @RequestBody patch: TacoOrder
    ): TacoOrder {
        val order:TacoOrder = orderRepository.findById(orderId).orElse(null)
        patch.deliveryName?.also { order.deliveryName = it }
        patch.deliveryStreet?.also { order.deliveryStreet = it }
        patch.deliveryCity?.also { order.deliveryCity = it }
        patch.deliveryState?.also { order.deliveryState = it }
        patch.deliveryZip?.also { order.deliveryZip = it }
        patch.ccNumber?.also { order.ccNumber = it }
        patch.ccExpiration?.also { order.ccExpiration = it }
        patch.ccCVV?.also { order.ccCVV = it }
        return orderRepository.save(order)
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable("orderId") orderId: Long) {
        try {
            orderRepository.deleteById(orderId)
        } catch (e: EmptyResultDataAccessException) {}
    }
}