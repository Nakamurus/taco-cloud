package com.example.tacocloud.controller

import com.example.tacocloud.model.RegistrationForm
import com.example.tacocloud.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/register")
class RegistrationController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping
    fun registerForm():String {
        return "registration.html"
    }

    @PostMapping
    fun processRegistration(registrationForm: RegistrationForm):String {
        userRepository.save(registrationForm.toUser(passwordEncoder))
        return "redirect:/login"
    }
}