package com.example.tacocloud.repository

import com.example.tacocloud.model.Ingredient

interface IngredientRepository {
    fun findAll(): Iterable<Ingredient>

    fun findById(id: String): Ingredient?

    fun save(ingredient: Ingredient): Ingredient
}