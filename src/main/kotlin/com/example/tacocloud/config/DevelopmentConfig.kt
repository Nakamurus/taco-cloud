package com.example.tacocloud.config

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Type
import com.example.tacocloud.model.User
import com.example.tacocloud.repository.IngredientRepository
import com.example.tacocloud.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder


@Profile("!prod")
class DevelopmentConfig {

    @Bean
    fun dataLoader(
        ingredientRepository: IngredientRepository,
        userRepository: UserRepository,
        encoder: PasswordEncoder
    ) = CommandLineRunner {
        ingredientRepository.deleteAll()
        userRepository.deleteAll()

        ingredientRepository.save(Ingredient("FLTO", "Flour Tortilla", Type.WRAP))
        ingredientRepository.save(Ingredient("COTO", "Corn Tortilla", Type.WRAP))
        ingredientRepository.save(Ingredient("GRBF", "Ground Beef", Type.PROTEIN))
        ingredientRepository.save(Ingredient("CARN", "Carnitas", Type.PROTEIN))
        ingredientRepository.save(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES))
        ingredientRepository.save(Ingredient("LETC", "Lettuce", Type.VEGGIES))
        ingredientRepository.save(Ingredient("CHED", "Cheddar", Type.CHEESE))
        ingredientRepository.save(Ingredient("JACK", "Monterrey Jack", Type.CHEESE))
        ingredientRepository.save(Ingredient("SLSA", "Salsa", Type.SAUCE))
        ingredientRepository.save(Ingredient("SRCR", "Sour Cream", Type.SAUCE))

        userRepository.save(User("habuma", encoder.encode("password"),
            "Craig Walls", "123 North Street", "Cross Roads", "TX",
            "76227", "123-123-1234"))
    }
}