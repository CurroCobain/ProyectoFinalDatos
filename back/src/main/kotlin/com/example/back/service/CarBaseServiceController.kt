package com.example.back.service

import com.example.back.service.car.Car
import com.example.back.service.car.CarService
import com.example.back.service.carBase.CarBase
import com.example.back.service.carBase.CarBaseService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carBases")
class CarBaseServiceController @Autowired constructor(private val carBaseService: CarBaseService,
                                                      private val carService: CarService) {

    /**
     * Return all Car Bases
     */
    @GetMapping("/allBases")
    fun getAllCarBases(): ResponseEntity<Any> {
        return ResponseEntity(carBaseService.GetAllCarBases(), HttpStatus.OK)
    }
    @GetMapping("/{id}")
    fun getACarBase(id: Long): ResponseEntity<Any> {
        return ResponseEntity(carBaseService.GetCarBaseById(id), HttpStatus.OK)
    }

    /**
     * Update a Car Base
     */
    @PutMapping("/{id}")
    @Transactional
    fun updateCarBase(@PathVariable("id") id: Long, @RequestBody carBase: CarBase): ResponseEntity<Any> {
        carBaseService.updateCarBase(id, carBase)
        return ResponseEntity("Car Base is updated successfully", HttpStatus.OK)
    }

    /**
     * Delete a Car Base
     */
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteCarBase(@PathVariable("id") id: Long): ResponseEntity<Any> {
        carBaseService.deleteCarBase(id)
        return ResponseEntity("Car Base is deleted successfully", HttpStatus.OK)
    }

    /**
     * Create a Car Base
     */
    @PostMapping
    @Transactional
    fun createCarBase(@RequestBody carBase: CarBase): ResponseEntity<Any> {
        carBaseService.addCarBase(carBase)
        return ResponseEntity("Car Base is created successfully", HttpStatus.CREATED)
    }

}
