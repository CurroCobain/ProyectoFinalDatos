package com.example.back.service

import com.example.back.service.car.Car
import com.example.back.service.car.CarService
import com.example.back.service.carBase.CarBaseService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarServiceController @Autowired constructor(private val carService: CarService) {

    /**
     * Return all Cars
     */
    @GetMapping("/allCars")
    fun getAllCars(): ResponseEntity<Any> {
        return ResponseEntity(carService.GetAllCars(), HttpStatus.OK)
    }

    /**
     * Update a Car
     */
    @PutMapping("/{id}")
    @Transactional
    fun updateCar(@PathVariable("id") id: Long, @RequestBody car: Car): ResponseEntity<Any> {
        carService.UpdateCar(id, car)
        return ResponseEntity("Car is updated successfully", HttpStatus.OK)
    }

    /**
     * Delete a Car
     */
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteCar(@PathVariable("id") id: Long): ResponseEntity<Any> {
        carService.DeleteCar(id)
        return ResponseEntity("Car is deleted successfully", HttpStatus.OK)
    }

    /**
     * Create a Car
     */
    @PostMapping("/add")
    @Transactional
    fun createCar(@RequestBody car: Car): ResponseEntity<Any> {
        carService.AddCar(car)
        return ResponseEntity("Car is created successfully", HttpStatus.CREATED)
    }
}