package com.example.back.service.car

import com.example.back.service.carBase.CarBase
import jakarta.persistence.*
import java.util.*

@Entity
data class Car(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,  // Identificador único del coche

    @Column(nullable = false, unique = true)
    val plate: String,  // Número de matrícula del coche, único y obligatorio

    @Column(nullable = false)
    val available: Boolean,  // Indica si el coche está disponible o no

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id", nullable = false)
    val base: CarBase  // Base a la que pertenece el coche, relación Many-to-One
)