package com.example.tacocloud.repository

import com.example.tacocloud.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}