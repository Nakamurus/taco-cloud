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
class TacoCloudApplication

fun main(args: Array<String>) {
	runApplication<TacoCloudApplication>(*args)
}

