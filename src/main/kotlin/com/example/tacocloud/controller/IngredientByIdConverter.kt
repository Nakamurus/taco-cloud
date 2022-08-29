package com.example.tacocloud.controller

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Type
import com.example.tacocloud.repository.IngredientRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class IngredientByIdConverter(
    private val ingredientRepository: IngredientRepository
    ) :Converter<String, Ingredient> {

    override fun convert(id: String): Ingredient? {
        return ingredientRepository.findById(id)
    }
}