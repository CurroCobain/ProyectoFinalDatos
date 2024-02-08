package com.example.back.service.car

import com.example.back.service.car.Car

interface  CarInterface {

    fun GetAllCars(): MutableList<Car?>

    fun GetCarById(id: Long): Car?

    fun AddCar(car: Car)

    fun UpdateCar(id:Long, updatedCar: Car): Boolean

    fun DeleteCar(id: Long): Boolean

    fun LoadInitialData()

}