package com.example.tacocloud.controller

import com.example.tacocloud.service.OrderAdminService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController(
    private val adminService: OrderAdminService
) {
    @GetMapping
    fun showAdminPage():String {
        return "admin"
    }

    @PostMapping("/deletedOrders")
    fun deleteAllOrders():String {
        adminService.deleteAllOrders()
        return "redirect:/admin"
    }
}