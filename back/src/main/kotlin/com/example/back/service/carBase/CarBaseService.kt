package com.example.back.service.carBase

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarBaseService(
    @Autowired
    private val carBases: CarBaseRepository
) : CarBaseInterface {

    override fun GetAllCarBases(): MutableList<CarBase?> {
        return carBases.findAll()
    }
    @Transactional
    override fun GetCarBaseById(id: Long): CarBase? {
        return carBases.findById(id).orElse(null)
    }
    @Transactional
    override fun addCarBase(carBase: CarBase) {
        carBases.save(carBase)
    }
    @Transactional
    override fun updateCarBase(id: Long, updatedCarBase: CarBase): Boolean {
        val existingCarBase = GetCarBaseById(id)
        return if (existingCarBase != null) {
            carBases.delete(existingCarBase)
            carBases.save(updatedCarBase)
            true
        } else {
            false
        }
    }
    @Transactional
    override fun deleteCarBase(id: Long): Boolean {
        val carBaseToRemove = GetCarBaseById(id)
        return if (carBaseToRemove != null) {
            carBases.delete(carBaseToRemove)
            true
        } else {
            false
        }
    }
    @Transactional
    override fun loadInitialData() {
        carBases.save(CarBase(1, "Base 001", false, "calle street nº1"))
        carBases.save(CarBase(2, "Base 002", false,"calle street nº2"))
        carBases.save(CarBase(3, "Base 003", false,"calle street nº3"))
    }
}