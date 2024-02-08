package com.example.back.service.carBase

import com.example.back.service.carBase.CarBase

interface CarBaseInterface {

    fun GetAllCarBases(): MutableList<CarBase?>

    fun GetCarBaseById(id: Long): CarBase?

    fun addCarBase(carBase: CarBase)

    fun updateCarBase(id: Long, updatedCarBase: CarBase): Boolean

    fun deleteCarBase(id: Long): Boolean

    fun loadInitialData()
}
