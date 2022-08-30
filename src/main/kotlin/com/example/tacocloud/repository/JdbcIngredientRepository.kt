package com.example.tacocloud.repository

import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Type
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcIngredientRepository(private val jdbcTemplate: JdbcTemplate): IngredientRepository {

    override fun findAll(): Iterable<Ingredient> {
        return jdbcTemplate.query(
            "seelct id, name, type from Ingredient",
            this::mapRowToIngredient
        )
    }

    override fun findById(id: String): Ingredient? {
        return jdbcTemplate.query(
            "select id, name, type from Ingredient where id=?",
            this::mapRowToIngredient,
            id
        ).firstOrNull()
    }

    override fun save(ingredient: Ingredient): Ingredient {
        jdbcTemplate.update(
            "insert into Ingredient (id, name, type) values (?, ?, ?)",
            ingredient.id,
            ingredient.name,
            ingredient.type.toString()
        )
        return ingredient
    }

    private fun mapRowToIngredient(row: ResultSet, rowNum: Int): Ingredient {
        return Ingredient(
            row.getString("id"),
            row.getString("name"),
            Type.valueOf(row.getString("type"))
        )
    }
}