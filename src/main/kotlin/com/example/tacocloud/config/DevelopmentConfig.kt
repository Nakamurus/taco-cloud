package com.example.tacocloud.config

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Taco
import com.example.tacocloud.model.Type
import com.example.tacocloud.model.User
import com.example.tacocloud.repository.IngredientRepository
import com.example.tacocloud.repository.TacoRepository
import com.example.tacocloud.repository.UserRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder


@Profile("!prod")
@Configuration
class DevelopmentConfig {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun dataLoader(
        ingredientRepository: IngredientRepository,
        userRepository: UserRepository,
        encoder: PasswordEncoder,
        tacoRepository: TacoRepository
    ) = CommandLineRunner {
        ingredientRepository.deleteAll()
        userRepository.deleteAll()

        val flourTortilla= Ingredient(
            "FLTO", "Flour Tortilla", Type.WRAP
        )
        val cornTortilla = Ingredient(
            "COTO", "Corn Tortilla", Type.WRAP
        )
        val groundBeef= Ingredient(
            "GRBF", "Ground Beef", Type.PROTEIN
        )
        val carnitas = Ingredient(
            "CARN", "Carnitas", Type.PROTEIN
        )
        val tomatoes = Ingredient(
            "TMTO", "Diced Tomatoes", Type.VEGGIES
        )
        val lettuce = Ingredient(
            "LETC", "Lettuce", Type.VEGGIES
        )
        val cheddar = Ingredient(
            "CHED", "Cheddar", Type.CHEESE
        )
        val jack = Ingredient(
            "JACK", "Monterrey Jack", Type.CHEESE
        )
        val salsa = Ingredient(
            "SLSA", "Salsa", Type.SAUCE
        )
        val sourCream = Ingredient(
            "SRCR", "Sour Cream", Type.SAUCE
        )

        ingredientRepository.save(flourTortilla)
        ingredientRepository.save(cornTortilla)
        ingredientRepository.save(groundBeef)
        ingredientRepository.save(carnitas)
        ingredientRepository.save(tomatoes)
        ingredientRepository.save(lettuce)
        ingredientRepository.save(cheddar)
        ingredientRepository.save(jack)
        ingredientRepository.save(salsa)
        ingredientRepository.save(sourCream)

        userRepository.save(User("habuma", encoder.encode("password"),
            "Craig Walls", "123 North Street", "Cross Roads", "TX",
            "76227", "123-123-1234"))

        val tacos = listOf(
            Taco().also { it.name = "Carnivore"
                it.ingredients = mutableListOf(
                flourTortilla, groundBeef, carnitas,
                sourCream, salsa, cheddar
                )},
            Taco().also { it.name = "Bovine Bounty"
                it.ingredients = mutableListOf(
                    cornTortilla, groundBeef, cheddar,
                    jack, sourCream
                )},
            Taco().also { it.name = "Veg-Out"
                it.ingredients = mutableListOf(
                    flourTortilla, cornTortilla, tomatoes,
                    lettuce, salsa
                )}
            )
        tacoRepository.saveAll(tacos)

        logger.info("Finished prepopulating with test data")
        logger.info(tacoRepository.findAll().toString())
        logger.info(ingredientRepository.findAll().toString())

    }
}