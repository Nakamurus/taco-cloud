package com.example.tacocloud.model

import com.example.tacocloud.model.Taco
import org.hibernate.validator.constraints.CreditCardNumber
import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class TacoOrder(
    @field:NotBlank(message = "Delivery name is required")
    var deliveryName: String? = null,

    @field:NotBlank(message = "Delivery Street is required")
    var deliveryStreet: String? = null,

    @field:NotBlank(message = "Delivery Cityis required")
    var deliveryCity:String? = null,

    @field:NotBlank(message = "Delivery States is required")
    var deliveryState: String? = null,

    @field:NotBlank(message = "Delivery Zip Code is required")
    var deliveryZip: String? = null,

    @field:CreditCardNumber(message = "Not a valid credit card number")
    var ccNumber: String? = null,

    @field:Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
        message = "Must be formatted MM/YY"
    )
    var ccExpiration: String? = null,

    @field:Digits(integer=3, fraction=0, message = "Invalid CVV")
    var ccCVV: String? = null,

    var tacos: MutableList<Taco> = mutableListOf()
) {
    fun addTaco(taco: Taco) {
        this.tacos.add(taco)
    }
}