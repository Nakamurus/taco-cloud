package com.example.tacocloud

class TacoOrder(
    var deliveryName: String? = null,
    var deliveryStreet: String? = null,
    var deliveryCity:String? = null,
    var deliveryState: String? = null,
    var deliveryZip: String? = null,
    var ccNumber: String? = null,
    var ccExpiration: String? = null,
    var ccCVV: String? = null,
    var tacos: MutableList<Taco> = mutableListOf()
) {
    fun addTaco(taco: Taco) {
        this.tacos.add(taco)
    }
}