package com.example.tacocloud.repository

import com.example.tacocloud.model.Ingredient
import org.springframework.data.repository.CrudRepository

interface IngredientRepository: CrudRepository<Ingredient, String> {

    fun save(ingredient: Ingredient): Ingredient
}