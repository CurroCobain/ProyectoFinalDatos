package com.example.back

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
// @ComponentScan(basePackages = ["com.example.back","com.example.init"])
class BackApplication

fun main(args: Array<String>) {
    runApplication<BackApplication>(*args)

}
