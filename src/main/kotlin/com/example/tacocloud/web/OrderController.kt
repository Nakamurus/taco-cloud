package com.example.tacocloud.web

import com.example.tacocloud.TacoOrder
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController {

    @GetMapping("/current")
    fun orderForm():String {
        return "orderForm"
    }

    val logger = KotlinLogging.logger {}
    @PostMapping
    fun processOrder(order: TacoOrder, sessionStatus: SessionStatus): String {
        logger.info("Order submitted: {}", order)
        sessionStatus.setComplete() // cleans up session, which holds TacoOrder object

        return "redirect:/"
    }

}