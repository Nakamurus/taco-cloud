package com.example.tacocloud.api

import com.example.tacocloud.model.Ingredient
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TacoCloudClient(
    private val rest: RestTemplate
) {
    private val base_url = "http://localhost:8080"

    // GET
    fun getIngredientById(ingredientId: String): Ingredient? {
        return rest.getForObject(
            "$base_url/ingredients/{id}",
            Ingredient::class.java, ingredientId
        )
    }

    fun getAllIngredients(): List<Ingredient>? {
        return rest.exchange("$base_url/ingredients",
            HttpMethod.GET, null,
            object: ParameterizedTypeReference<List<Ingredient>>() {})
            .body
    }

    // PUT
    fun updateIngredient(ingredient: Ingredient) {
        rest.put("$base_url/ingredients/{id}",
            ingredient, ingredient.id
        )
    }

    // POST
    fun createIngredient(ingredient: Ingredient): Ingredient? {
        return rest.postForObject("$base_url/ingredients",
            ingredient, Ingredient::class.java
        )
    }

    // DELETE
    fun deleteIngredient(ingredient: Ingredient) {
        rest.delete("$base_url/ingredients/{id}",
            ingredient.id
        )
    }
}