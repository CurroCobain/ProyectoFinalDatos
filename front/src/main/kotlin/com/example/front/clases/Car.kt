package com.example.front.clases

data class CarDTO (
    val id: Long? = null,
    val plate: String?,
    val available: Boolean?,
    val base: CarBaseDTO?
){
    override fun toString(): String {
        return "matrícula => ${this.plate} \n "

    }
}