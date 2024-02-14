package com.example.back

import com.example.back.service.car.CarService
import com.example.back.service.carBase.CarBaseService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val carBaseService: CarBaseService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Lógica para cargar datos en tu servicio
        carBaseService.loadInitialData()
    }
}



