package com.example.tacocloud

class TacoOrder(
    val deliveryName: String,
    val deliveryStreet: String,
    val deliveryState: String,
    val deliveryZip: String,
    val ccNumber: String,
    val ccExpiration: String,
    val ccCvv: String,
    private val tacos: MutableList<Taco> = mutableListOf(),
) {

    fun addTaco(taco: Taco) {
        this.tacos.add(taco)
    }
}