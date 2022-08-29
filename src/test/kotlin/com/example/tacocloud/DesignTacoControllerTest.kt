package com.example.tacocloud

import com.example.tacocloud.controller.DesignTacoController
import com.example.tacocloud.model.Ingredient
import com.example.tacocloud.model.Type
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType

@ExtendWith(SpringExtension::class)
@WebMvcTest(DesignTacoController::class)
class DesignTacoControllerTest(@Autowired val mockMvc: MockMvc,
                               var ingredients: List<Ingredient>) {

    @BeforeEach
    fun setup() {
        ingredients = listOf(
            Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            Ingredient("CARN", "Carnitas", Type.PROTEIN),
            Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            Ingredient("LETC", "Lettuce", Type.VEGGIES),
            Ingredient("CHED", "Cheddar", Type.CHEESE),
            Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            Ingredient("SLSA", "Salsa", Type.SAUCE),
            Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        )
    }

    @Test
    fun testShowDesignForm() {
        mockMvc.perform(get("/design"))
            .andExpect(status().isOk)
            .andExpect(view().name("design"))
            .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
            .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
            .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
            .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
            .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

    @Test
    fun processTaco() {
        mockMvc.perform(post("/design")
            .content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection)
            .andExpect(header().stringValues("Location", "/orders/current"))
    }
}