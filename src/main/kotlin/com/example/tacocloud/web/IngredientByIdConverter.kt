package com.example.tacocloud.web

import com.example.tacocloud.Ingredient
import com.example.tacocloud.Type
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class IngredientByIdConverter(private final val ingredientMap: HashMap<String, Ingredient> = hashMapOf()): Converter<String, Ingredient> {
    init {
        ingredientMap["FLTO"] = Ingredient("FLTO", "Flour Tortilla", Type.WRAP)
        ingredientMap["COTO"] = Ingredient("COTO", "Corn Tortilla", Type.WRAP)
        ingredientMap["GRBF"] = Ingredient("GRBF", "Ground Beef", Type.PROTEIN)
        ingredientMap["CARN"] = Ingredient("CARN", "Carnitas", Type.PROTEIN)
        ingredientMap["TMTO"] = Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES)
        ingredientMap["LETC"] = Ingredient("LETC", "Lettuce", Type.VEGGIES)
        ingredientMap["CHED"] = Ingredient("CHED", "Cheddar", Type.CHEESE)
        ingredientMap["JACK"] = Ingredient("JACK", "Monterrey Jack", Type.CHEESE)
        ingredientMap["SLSA"] = Ingredient("SLSA", "Salsa", Type.SAUCE)
        ingredientMap["SRCR"] = Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    }

    override fun convert(id: String):Ingredient? {
        return ingredientMap[id]
    }
}