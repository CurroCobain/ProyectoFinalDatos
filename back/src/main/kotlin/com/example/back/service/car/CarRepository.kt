package com.example.back.service.car

import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository: JpaRepository<Car?, Long> {
}