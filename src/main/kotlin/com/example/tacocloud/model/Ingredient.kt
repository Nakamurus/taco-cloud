package com.example.tacocloud.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Ingredient(@Id val id: String, val name: String, val type: Type)

enum class Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
}