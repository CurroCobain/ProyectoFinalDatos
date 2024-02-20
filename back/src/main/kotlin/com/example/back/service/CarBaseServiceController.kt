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
class CarBaseServiceController @Autowired constructor(
    private val carBaseService: CarBaseService,  // Servicio para gestionar las bases de coches
    private val carService: CarService  // Servicio para gestionar los coches
) {

    /**
     * Obtiene todas las bases de coches.
     */
    @GetMapping("/allBases")
    fun getAllCarBases(): ResponseEntity<Any> {
        return ResponseEntity(carBaseService.GetAllCarBases(), HttpStatus.OK)
    }

    /**
     * Obtiene una base de coches por su ID.
     * @param id ID de la base de coches a buscar.
     */
    @GetMapping("/{id}")
    fun getACarBase(@PathVariable("id")id: Long): ResponseEntity<Any> {
        return ResponseEntity(carBaseService.GetCarBaseById(id), HttpStatus.OK)
    }

    /**
     * Actualiza una base de coches existente.
     * @param id ID de la base de coches a actualizar.
     * @param carBase La base de coches con los datos actualizados.
     */
    @PutMapping("/{id}")
    @Transactional
    fun updateCarBase(@PathVariable("id") id: Long, @RequestBody carBase: CarBase): ResponseEntity<Any> {
        carBaseService.updateCarBase(id, carBase)
        return ResponseEntity("Car Base is updated successfully", HttpStatus.OK)
    }

    /**
     * Elimina una base de coches existente.
     * @param id ID de la base de coches a eliminar.
     */
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteCarBase(@PathVariable("id") id: Long): ResponseEntity<Any> {
        carBaseService.deleteCarBase(id)
        return ResponseEntity("Car Base is deleted successfully", HttpStatus.OK)
    }

    /**
     * Crea una nueva base de coches.
     * @param carBase La base de coches a crear.
     */
    @PostMapping("/add")
    @Transactional
    fun createCarBase(@RequestBody carBase: CarBase): ResponseEntity<Any> {
        carBaseService.addCarBase(carBase)
        return ResponseEntity("Car Base is created successfully", HttpStatus.CREATED)
    }
}