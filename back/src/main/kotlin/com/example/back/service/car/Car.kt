package com.example.back.service.car

import com.example.back.service.carBase.CarBase
import jakarta.persistence.*
import java.util.*

@Entity
data class Car(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val plate: String,
    val available: Boolean,

    @ManyToOne
    @JoinColumn(name = "base_id")
    val base: CarBase?
)