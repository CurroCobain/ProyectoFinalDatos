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
     * Método para obtener todos los coches.
     */
    @GetMapping("/allCars")
    fun getAllCars(): ResponseEntity<Any> {
        return ResponseEntity(carService.GetAllCars(), HttpStatus.OK)
    }

    /**
     * Método para actualizar un coche.
     * @param id ID del coche a actualizar.
     * @param car Objeto Car con los datos actualizados.
     */
    @PutMapping("/{id}")
    @Transactional
    fun updateCar(@PathVariable("id") id: Long, @RequestBody car: Car): ResponseEntity<Any> {
        carService.UpdateCar(id, car)
        return ResponseEntity("Car is updated successfully", HttpStatus.OK)
    }

    /**
     * Método para eliminar un coche.
     * @param id ID del coche a eliminar.
     */
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteCar(@PathVariable("id") id: Long): ResponseEntity<Any> {
        carService.DeleteCar(id)
        return ResponseEntity("Car is deleted successfully", HttpStatus.OK)
    }

    /**
     * Método para crear un coche nuevo.
     * @param car Objeto Car con los datos del coche a crear.
     */
    @PostMapping("add")
    @Transactional
    fun createCar(@RequestBody car: Car): ResponseEntity<Any> {
        carService.AddCar(car)
        return ResponseEntity("Car is created successfully", HttpStatus.CREATED)
    }
}