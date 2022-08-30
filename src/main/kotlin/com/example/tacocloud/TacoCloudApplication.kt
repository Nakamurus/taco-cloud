package com.example.tacocloud

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Type
import com.example.tacocloud.repository.IngredientRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TacoCloudApplication {
	val logger = KotlinLogging.logger {}
	@Bean
	fun dataLoader(ingredientRepository: IngredientRepository) = CommandLineRunner {
		ingredientRepository.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
		ingredientRepository.save(Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		ingredientRepository.save(Ingredient("COTO", "Corn Tortilla", Type.WRAP));
		ingredientRepository.save(Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		ingredientRepository.save(Ingredient("CARN", "Carnitas", Type.PROTEIN));
		ingredientRepository.save(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
		ingredientRepository.save(Ingredient("LETC", "Lettuce", Type.VEGGIES));
		ingredientRepository.save(Ingredient("CHED", "Cheddar", Type.CHEESE));
		ingredientRepository.save(Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
		ingredientRepository.save(Ingredient("SLSA", "Salsa", Type.SAUCE));
		ingredientRepository.save(Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		logger.info("prepopulated ingredient repository: {}", ingredientRepository.findAll())
	}
}

fun main(args: Array<String>) {
	runApplication<TacoCloudApplication>(*args)
}

