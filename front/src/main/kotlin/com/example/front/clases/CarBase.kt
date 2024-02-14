package com.example.front.clases

data class CarBaseDTO(
    var id: Long? = null,
    val name: String?,
    val active: Boolean?,
    val address: String?,
    val cars: List<CarDTO?> = mutableListOf()
){
    override fun toString(): String {
        return "nombre => ${this.name} \n"

    }
}