package com.example.front

import com.example.back.service.carBase.CarBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.fxml.FXML
import javafx.scene.control.Label
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class HelloController {

    private val httpClient = HttpClient.newHttpClient()
    private val apiUrl = "http://localhost:8080/carBases/allBases"


    private val gson = Gson()

    private fun parseJsonToTasks(json: String): List<CarBase> {
        val carBasesList = object : TypeToken<List<CarBase>>() {}.type
        return gson.fromJson(json, carBasesList)
    }

    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        val response = sendGetRequest(apiUrl)
        val carBaseList = parseJsonToTasks(response)
        // Ahora puedes trabajar con la lista de bases
        // Por ejemplo, puedes imprimir los nombres de las bases en la consola
        carBaseList.forEach { welcomeText.text += it.name }
    }

    private fun sendGetRequest(url: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()

        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        return responseBody
    }

    /*
    private fun postCarBase(): String {
        val url = "http://localhost:8080/carBases"
        // Crear el cliente HTTP
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)

    }

     */
}