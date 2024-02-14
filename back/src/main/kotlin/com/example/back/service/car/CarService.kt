package com.example.back.service.car

import com.example.back.service.carBase.CarBaseService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarService(
    @Autowired
    private val cars: CarRepository,
    private val carBaseService: CarBaseService
): CarInterface {

    override fun GetAllCars(): MutableList<Car?>{
        return cars.findAll()
    }

    @Transactional
    override fun GetCarById(id: Long): Car? {
        return cars.findById(id).orElse(null)
    }

    @Transactional
    override fun AddCar(car: Car){
        cars.save(car)
    }

    @Transactional
    override fun UpdateCar(id: Long, updatedCar: Car): Boolean {
        val existingCar = GetCarById(id)
        return if (existingCar != null) {
            cars.delete(existingCar)
            cars.save(updatedCar)
            true
        } else {
            false
        }
    }

    @Transactional
    override fun DeleteCar(id: Long): Boolean {
        val carToRemove = GetCarById(id)
        return if (carToRemove != null){
            cars.delete(carToRemove)
            true
        } else{
            false
        }
    }

    @Transactional
    override fun LoadInitialData() {
        val carBase1 = carBaseService.GetCarBaseById(1)
        val carBase2 = carBaseService.GetCarBaseById(2)
        val carBase3 = carBaseService.GetCarBaseById(3)

        cars.save(Car(1, "1234ABC", true, carBase1))
        cars.save(Car(1, "1234ABC", true, carBase2))
        cars.save(Car(1, "1234ABC", true, carBase3))

    }
}