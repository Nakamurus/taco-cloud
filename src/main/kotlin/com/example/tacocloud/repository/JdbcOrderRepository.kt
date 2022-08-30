package com.example.tacocloud.repository

import com.example.tacocloud.model.IngredientRef
import com.example.tacocloud.model.Taco
import com.example.tacocloud.model.TacoOrder
import org.springframework.asm.Type
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.PreparedStatementCreatorFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types
import java.util.Date

@Repository
class JdbcOrderRepository(private val jdbcOperations: JdbcOperations): OrderRepository {

    @Transactional
    override fun save(order: TacoOrder): TacoOrder {
        val pscf =
            PreparedStatementCreatorFactory(
                """
                    insert into Taco_Order
                    (delivery_name, delivery_street, delivery_city,
                    delivery_state, delivery_zip, cc_number,
                    cc_expiration, cc_cvv, placed_at)
                    values (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """.trimIndent(),
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
            )
        pscf.setReturnGeneratedKeys(true)

        order.placedAt = Date()
        val psc: PreparedStatementCreator =
            pscf.newPreparedStatementCreator(
                listOf(
                    order.deliveryName,
                    order.deliveryStreet,
                    order.deliveryCity,
                    order.deliveryState,
                    order.deliveryZip,
                    order.ccNumber,
                    order.ccExpiration,
                    order.ccCVV,
                    order.placedAt
                )
            )
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(psc, keyHolder)
        keyHolder.key?.let {
            order.id = it.toLong()
            val tacos: List<Taco> = order.tacos
            tacos.forEachIndexed { index, taco ->
                saveTaco(it.toLong(), index, taco)
            }
        }
        return order
    }

    private fun saveTaco(
        orderId: Long, orderKey: Int, taco: Taco): Long? {

        taco.createdAt = Date()
        val pscf =
            PreparedStatementCreatorFactory(
                """
                    insert into Taco
                    (name, created_at, taco_order, taco_order_key)
                    values (?, ?, ?, ?)
                """.trimIndent(),
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
            )
        pscf.setReturnGeneratedKeys(true)

        val psc:PreparedStatementCreator =
            pscf.newPreparedStatementCreator(
                listOf(
                    taco.name,
                    taco.createdAt,
                    orderId,
                    orderKey
                )
            )
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(psc, keyHolder)
        return keyHolder.key?.also {
            val tacoId = it.toLong()
            taco.id = tacoId
            saveIngredientRefs(tacoId, taco.ingredients)
        }?.toLong()
    }

    private fun saveIngredientRefs(
        tacoId: Long, ingredientRefs: List<IngredientRef>
    ) {
        ingredientRefs.forEachIndexed { index, ingredientRef ->
            jdbcOperations.update(
                """
                    insert into Ingredient_Ref (ingredient, taco, taco_key)
                    values (?, ?, ?)
                """.trimIndent(),
                ingredientRef.ingredient, tacoId, index
            )
        }
    }

    override fun findById(id: Long): TacoOrder? {
        return jdbcOperations.queryForObject(
            """
                select id, delivery_name, delivery_street, delivery_city,
                delivery_state, delivery_zip, cc_number, cc_expiration,
                cc_cvv, placed_at from Taco_Order where id=?
            """.trimIndent(),

            {row, _ ->
                TacoOrder().also {
                    it.id = row.getLong("id")
                    it.deliveryName = row.getString("delivery_name")
                    it.deliveryStreet = row.getString("delivery_city")
                    it.deliveryCity = row.getString("delivery_city")
                    it.deliveryState = row.getString("delivery_state")
                    it.deliveryZip = row.getString("delivery_zip")
                    it.ccNumber = row.getString("cc_number")
                    it.ccExpiration = row.getString("cc_expiration")
                    it.ccCVV = row.getString("cc_cvv")
                    it.placedAt = Date(row.getTimestamp("placed_at").time)
                    it.tacos = findTacosByOrderId(row.getLong("id")).toMutableList()
                }
        }, id)
    }

    private fun findTacosByOrderId(orderId: Long): List<Taco> {
        return jdbcOperations.query(
            """
                select id, name, created_at, from Taco
                where taco_order=? order by taco_order_key
            """.trimIndent(),
            {row, _ ->
                Taco().also {
                    it.id = row.getLong("id")
                    it.name = row.getString("name")
                    it.createdAt = Date(row.getTimestamp("created_at").time)
                    it.ingredients = findIngredientsByTacoId(row.getLong("id"))
                }
            }, orderId
        )
    }

    private fun findIngredientsByTacoId(tacoId: Long): List<IngredientRef> {
        return jdbcOperations.query(
            """
                select ingredient from Ingredient_Ref
                where taco = ? order by taco_key
            """.trimIndent(),
            { row, _ ->
            IngredientRef(row.getString("ingredient"))
        },
            tacoId
        )
    }
}