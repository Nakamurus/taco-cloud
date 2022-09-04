package com.example.tacocloud.controller

import com.example.tacocloud.model.*
import com.example.tacocloud.repository.IngredientRepository
import com.example.tacocloud.repository.TacoRepository
import com.example.tacocloud.repository.UserRepository
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
import java.security.Principal
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import javax.validation.Valid


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController(
    private val ingredientRepository: IngredientRepository,
    private val tacoRepository: TacoRepository,
    private val userRepository: UserRepository
    ) {
    private val logger = KotlinLogging.logger {}
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

    @ModelAttribute(name = "user")
    fun user(principal: Principal): User? {
        val username: String = principal.name
        return userRepository.findByUsername(username)
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

        logger.info { "--- Saving taco" }

        if (errors.hasErrors()) {
            return "design"
        }

        val saved: Taco = tacoRepository.save(taco)

        logger.info("Processing taco: {} with ingredients {}", saved.name, saved.ingredients)

        tacoOrder.addTaco(saved)
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