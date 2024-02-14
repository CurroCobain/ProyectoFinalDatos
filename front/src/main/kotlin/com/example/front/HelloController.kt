package com.example.front

import com.example.front.clases.CarBaseDTO
import com.example.front.clases.CarDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.fxml.FXML
import javafx.scene.control.Label
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class HelloController {
    private val apiUrlBasic = "http://localhost:8080/cars/add"
    private val apiUrl = "http://localhost:8080/carBases/allBases"
    private val httpClient = HttpClient.newHttpClient()
    private val gson = Gson()

    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        sendPostRequest(apiUrlBasic)
        val response = sendGetRequest(apiUrl)
        val carBaseList = parseJsonToCarBasesDTO(response)
        // Ahora puedes trabajar con la lista de bases
        // Por ejemplo, puedes imprimir los nombres de las bases en la consola
        carBaseList.forEach { welcomeText.text += it.toString() }
    }



    /*
    private fun postCarBase(): String {
        val url = "http://localhost:8080/carBases"
        // Crear el cliente HTTP
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)

    }

     */


    fun parseJsonToCarBasesDTO(json: String): List<CarBaseDTO> {
        val carBasesList = object : TypeToken<List<CarBaseDTO>>() {}.type
        return gson.fromJson(json, carBasesList)
    }
    private fun parseJsonToCarBaseDTO(json: String): CarBaseDTO {
        val carBasesDTO = object : TypeToken<CarBaseDTO>() {}.type
        return gson.fromJson(json, carBasesDTO)
    }
    fun sendGetRequest(url: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()

        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        return responseBody
    }
    fun getCarBaseById(identifier: Long): CarBaseDTO {
        val apiUrlId = "http://localhost:8080/$identifier"
        val response = sendGetRequest(apiUrlId)
        val carBase1 = parseJsonToCarBaseDTO(response)
        return carBase1
    }


    fun sendPostRequest(url: String): String{
        val car1 = CarDTO(21,
            "1234ABC",
            true,
            null)
        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(car1)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        return responseBody
    }
}