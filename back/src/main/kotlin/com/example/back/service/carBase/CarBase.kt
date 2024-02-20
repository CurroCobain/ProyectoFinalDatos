package com.example.back.service.carBase
import com.example.back.service.car.Car
import jakarta.persistence.*

@Entity
data class CarBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,  // Identificador único de la base de coches

    @Column(nullable = false)
    val name: String,  // Nombre de la base de coches, obligatorio

    @Column(nullable = false)
    val active: Boolean,  // Indica si la base de coches está activa o no

    @Column(nullable = false)
    val address: String  // Dirección de la base de coches, obligatoria
)

