package com.example.back.service.carBase

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarBaseService(
    @Autowired
    private val carBases: CarBaseRepository  // Repositorio para acceder a los datos de las bases de coches
) : CarBaseInterface {

    /**
     * Obtiene todas las bases de coches.
     */
    override fun GetAllCarBases(): MutableList<CarBase?> {
        return carBases.findAll()  // Obtiene todas las bases de coches del repositorio
    }

    /**
     * Obtiene una base de coches por su ID.
     * @param id ID de la base de coches a buscar.
     * @return La base de coches correspondiente al ID, o null si no se encuentra.
     */
    @Transactional
    override fun GetCarBaseById(id: Long): CarBase? {
        return carBases.findById(id).orElse(null)  // Busca una base de coches por su ID en el repositorio
    }

    /**
     * Agrega una nueva base de coches.
     * @param carBase La base de coches a agregar.
     */
    @Transactional
    override fun addCarBase(carBase: CarBase) {
        carBases.save(carBase)  // Guarda la base de coches en el repositorio
    }

    /**
     * Actualiza una base de coches existente.
     * @param id ID de la base de coches a actualizar.
     * @param updatedCarBase La base de coches con los datos actualizados.
     * @return true si se actualizó correctamente, false si la base de coches no existe.
     */
    @Transactional
    override fun updateCarBase(id: Long, updatedCarBase: CarBase): Boolean {
        val existingCarBase = GetCarBaseById(id)  // Obtiene la base de coches existente por su ID
        return if (existingCarBase != null) {
            updatedCarBase.id = id  // Asigna el ID a la base de coches actualizada
            carBases.save(updatedCarBase)  // Guarda la base de coches actualizada en el repositorio
            true
        } else {
            false  // La base de coches no existe, no se puede actualizar
        }
    }

    /**
     * Elimina una base de coches existente.
     * @param id ID de la base de coches a eliminar.
     * @return true si se eliminó correctamente, false si la base de coches no existe.
     */
    @Transactional
    override fun deleteCarBase(id: Long): Boolean {
        val carBaseToRemove = GetCarBaseById(id)  // Obtiene la base de coches por su ID
        return if (carBaseToRemove != null) {
            carBases.delete(carBaseToRemove)  // Elimina la base de coches del repositorio
            true
        } else {
            false  // La base de coches no existe, no se puede eliminar
        }
    }

    /**
     * Carga datos iniciales de bases de coches en el repositorio.
     * Crea y guarda bases de coches de ejemplo.
     */
    @Transactional
    override fun loadInitialData() {
        // Crea y guarda bases de coches de ejemplo en el repositorio
        carBases.save(CarBase(1, "Base 001", false, "calle street nº1"))
        carBases.save(CarBase(2, "Base 002", false,"calle street nº2"))
        carBases.save(CarBase(3, "Base 003", false,"calle street nº3"))
    }
}