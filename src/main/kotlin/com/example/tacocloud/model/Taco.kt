package com.example.tacocloud.model
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class Taco(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var createdAt: Date = Date(),

    @field: NotNull
    @field: Size(min=5, message = "Name must be at least 5 characters long")
    var name: String? = null,

    @field: Size(min=1, message = "You must choose at least 1 ingredient")
    @ManyToMany()
    var ingredients: List<Ingredient> = mutableListOf()
)