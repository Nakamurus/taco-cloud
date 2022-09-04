package com.example.tacocloud.controller

import com.example.tacocloud.model.TacoOrder
import com.example.tacocloud.model.User
import com.example.tacocloud.repository.OrderRepository
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import javax.validation.Valid

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController(private val orderRepository: OrderRepository) {

    @GetMapping("/current")
    fun orderForm(
        @AuthenticationPrincipal user: User,
        @ModelAttribute order: TacoOrder
    ):String {
        if (order.deliveryName.isNullOrEmpty()) {
            order.deliveryName = user.fullname
        }

        if (order.deliveryStreet.isNullOrEmpty()) {
            order.deliveryStreet = user.street
        }

        if (order.deliveryCity.isNullOrEmpty()) {
            order.deliveryCity = user.city
        }

        if (order.deliveryState.isNullOrEmpty()) {
            order.deliveryState = user.state
        }

        if (order.deliveryZip.isNullOrEmpty()) {
            order.deliveryZip = user.zip
        }

        return "orderForm"
    }

    val logger = KotlinLogging.logger {}
    @PostMapping
    fun processOrder(@Valid order: TacoOrder, errors: Errors,
                     sessionStatus: SessionStatus,
    @AuthenticationPrincipal user: User): String {
        if (errors.hasErrors()) {
            return "orderForm"
        }

        order.user = user

        orderRepository.save(order)
        logger.info("Order submitted: {}", order.toString())
        sessionStatus.setComplete() // cleans up session, which holds TacoOrder object

        return "redirect:/"
    }

}