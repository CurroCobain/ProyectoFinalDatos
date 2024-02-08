package com.example.back.service.carBase
import com.example.back.service.car.Car
import jakarta.persistence.*

@Entity
data class CarBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val active: Boolean,
    val address: String,

    @OneToMany(mappedBy = "base", cascade = [CascadeType.ALL], orphanRemoval = true)
    val cars: List<Car> = mutableListOf()
)