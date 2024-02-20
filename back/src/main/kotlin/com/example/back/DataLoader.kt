package com.example.back

import com.example.back.service.car.CarService
import com.example.back.service.carBase.CarBaseService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 * Función que se ha utilizado para cargar los datos iniciales de la base de datos
 */
@Component
class DataLoader(private val carBaseService: CarBaseService,
    private val carService: CarService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Lógica para cargar datos en el servicio
        //carBaseService.loadInitialData()
        //carService.LoadInitialData()
    }
}



