package com.example.tacocloud.repository

import com.example.tacocloud.model.Taco
import org.springframework.data.repository.CrudRepository

interface TacoRepository: CrudRepository<Taco, Long> {
}