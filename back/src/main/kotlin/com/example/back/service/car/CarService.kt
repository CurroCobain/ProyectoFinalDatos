package com.example.back.service.car

import com.example.back.service.carBase.CarBaseService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarService(
    @Autowired
    private val cars: CarRepository,  // Repositorio para acceder a los datos de los coches
    private val carBaseService: CarBaseService,  // Servicio para acceder a los datos de las bases de coches
): CarInterface {

    /**
     * Obtiene todos los coches.
     */
    override fun GetAllCars(): MutableList<Car?> {
        return cars.findAll()  // Obtiene todos los coches del repositorio
    }

    /**
     * Obtiene un coche por su ID.
     * @param id ID del coche a buscar.
     * @return El coche correspondiente al ID, o null si no se encuentra.
     */
    @Transactional
    override fun GetCarById(id: Long): Car? {
        return cars.findById(id).orElse(null)  // Busca un coche por su ID en el repositorio
    }

    /**
     * Agrega un nuevo coche.
     * @param car El coche a agregar.
     */
    @Transactional
    override fun AddCar(car: Car) {
        cars.save(car)  // Guarda el coche en el repositorio
    }

    /**
     * Actualiza un coche existente.
     * @param id ID del coche a actualizar.
     * @param updatedCar El coche con los datos actualizados.
     * @return true si se actualizó correctamente, false si el coche no existe.
     */
    @Transactional
    override fun UpdateCar(id: Long, updatedCar: Car): Boolean {
        val existingCar = GetCarById(id)  // Obtiene el coche existente por su ID
        return if (existingCar != null) {
            updatedCar.id = id  // Asigna el ID al coche actualizado
            cars.save(updatedCar)  // Guarda el coche actualizado en el repositorio
            true
        } else {
            false  // El coche no existe, no se puede actualizar
        }
    }

    /**
     * Elimina un coche existente.
     * @param id ID del coche a eliminar.
     * @return true si se eliminó correctamente, false si el coche no existe.
     */
    @Transactional
    override fun DeleteCar(id: Long): Boolean {
        val carToRemove = GetCarById(id)  // Obtiene el coche por su ID
        return if (carToRemove != null){
            cars.delete(carToRemove)  // Elimina el coche del repositorio
            true
        } else{
            false  // El coche no existe, no se puede eliminar
        }
    }

    /**
     * Carga datos iniciales de coches en el repositorio.
     * Utiliza el servicio de bases de coches para obtener las bases necesarias.
     */
    @Transactional
    override fun LoadInitialData() {
        // Obtiene bases de coches necesarias para los datos iniciales
        val carBase1 = carBaseService.GetCarBaseById(1)
        val carBase2 = carBaseService.GetCarBaseById(2)
        val carBase3 = carBaseService.GetCarBaseById(3)

        // Crea coches de ejemplo y los guarda en el repositorio
        val car1 = Car(21, "1234ABC", true, carBase1!!)
        val car2 = Car(22, "4567CBA", false, carBase2!!)
        val car3 = Car(23, "7890BCA", true, carBase3!!)

        cars.save(car1)
        cars.save(car2)
        cars.save(car3)
    }
}