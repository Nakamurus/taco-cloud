package com.example.tacocloud.controller

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Taco
import com.example.tacocloud.model.TacoOrder
import com.example.tacocloud.model.Type
import com.example.tacocloud.repository.IngredientRepository
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import javax.validation.Valid


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController(private val ingredientRepository: IngredientRepository) {

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        val ingredients: Iterable<Ingredient> = ingredientRepository.findAll()

        val types: Array<Type> = Type.values()
        types.forEach {
            model.addAttribute(
                it.toString().lowercase(),
                filterByType(ingredients, it)
            )
        }
    }
    
    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder {
        return TacoOrder()
    }

    @ModelAttribute(name = "taco")
    fun taco(): Taco {
        return Taco()
    }

    @GetMapping
    fun showDesignForm(): String {
        return "design"
    }

    @PostMapping
    fun processTaco(
        @Valid taco: Taco, errors: Errors,
        @ModelAttribute tacoOrder: TacoOrder
    ): String {
        if (errors.hasErrors()) {
            return "design"
        }

        val logger = KotlinLogging.logger {}
        logger.info("Processing taco: {} with ingredients {}", taco.name, taco.ingredients)

        tacoOrder.addTaco(taco)
        return "redirect:/orders/current"
    }

    fun filterByType(
        ingredients: Iterable<Ingredient>,
        type: Type
    ): Iterable<Ingredient> {
        return StreamSupport.stream(ingredients.spliterator(), false)
            .filter { it.type == type }
            .collect(Collectors.toList())
    }
}