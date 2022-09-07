package com.example.tacocloud.repository

import com.example.tacocloud.model.Taco
import org.springframework.data.repository.PagingAndSortingRepository

interface TacoRepository: PagingAndSortingRepository<Taco, Long> {
}