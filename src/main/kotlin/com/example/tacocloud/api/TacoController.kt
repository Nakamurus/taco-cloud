package com.example.tacocloud.api

import com.example.tacocloud.model.Taco
import com.example.tacocloud.repository.TacoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path=["/api/tacos"],
    produces = ["application/json"]
)
@CrossOrigin(origins = ["http://tacocloud:8080"])
class TacoController(
    private val tacoRepository: TacoRepository
) {
    @GetMapping(params = ["recent"])
    fun recentTacos(): Iterable<Taco> {
        val page: PageRequest = PageRequest.of(0, 12,
            Sort.by("createdAt").descending()
        )
        return tacoRepository.findAll(page).content
    }

    @GetMapping("/{id}")
    fun tacoById(@PathVariable("id") id: Long): Taco? {
        return tacoRepository.findById(id).orElse(null)
    }
}