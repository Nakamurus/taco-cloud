package com.example.tacocloud.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Ingredient(@Id var id: String? = null, var name: String? = null, var type: Type? = null)

enum class Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
}