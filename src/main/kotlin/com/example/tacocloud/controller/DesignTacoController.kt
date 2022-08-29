package com.example.tacocloud.controller

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Taco
import com.example.tacocloud.model.TacoOrder
import com.example.tacocloud.model.Type
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
import javax.validation.Valid


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController {

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        val ingredients: List<Ingredient> = listOf(
            Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            Ingredient("CARN", "Carnitas", Type.PROTEIN),
            Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            Ingredient("LETC", "Lettuce", Type.VEGGIES),
            Ingredient("CHED", "Cheddar", Type.CHEESE),
            Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            Ingredient("SLSA", "Salsa", Type.SAUCE),
            Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        )

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
        ingredients: List<Ingredient>,
        type: Type
    ): Iterable<Ingredient> {
        return ingredients
            .stream()
            .filter { it.type == type }
            .collect(Collectors.toList())
    }
}