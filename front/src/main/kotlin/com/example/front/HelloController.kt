package com.example.front

import com.example.front.clases.CarBaseDTO
import com.example.front.clases.CarDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextField
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
    private lateinit var infoLabel: Label

    @FXML
    private lateinit var textField1: TextField

    @FXML
    private lateinit var textField2: TextField

    @FXML
    private lateinit var textField3: TextField

    @FXML
    private lateinit var textField4: TextField

    private lateinit var myCarBase : CarBaseDTO

    @FXML
    private fun onSearchButtonClick() {
        myCarBase = getCarBaseById(textField1.text.toLong())
        textField1.text = myCarBase.id.toString()
        textField2.text = myCarBase.name
        textField3.text = myCarBase.address
        textField4.text = myCarBase.cars.toString()
        infoLabel.text = myCarBase.toString()
    }

    @FXML
    private fun onUpdateButtonClick() {
        infoLabel!!.text = "Se presionó el botón Actualizar."
        // Aquí iría la lógica para actualizar algo
    }

    @FXML
    private fun onDeleteButtonClick() {
        textField1.text = "prueba"
        // Aquí iría la lógica para borrar algo
    }
    @FXML
    private fun onCreateButtonClick() {
        infoLabel!!.text = "Se presionó el botón Crear."
        // Aquí iría la lógica para crear algo
    }

    @FXML
    private fun onBasesButtonClick(){
            infoLabel.text = ""
            sendPostRequest(apiUrlBasic)
            val response = sendGetRequest(apiUrl)
            val carBaseList = parseJsonToCarBasesDTO(response)
            // Ahora puedes trabajar con la lista de bases
            // Por ejemplo, puedes imprimir los nombres de las bases en la consola
            carBaseList.forEach { infoLabel.text += it.toString() }

    }
    private fun searchBasesByParameter(parameter: Long?) {
        infoLabel.text = "" // Limpiamos el contenido anterior
        val searchUrl = "http://localhost:8080/carBases/$parameter"
        val response = sendGetRequest(searchUrl)
        val carBaseList = parseJsonToCarBasesDTO(response)
        carBaseList.forEach { infoLabel.text += it.toString() }
    }

    @FXML
    private fun onTaxisButtonClick(){
        infoLabel.text = "taxis buscados"
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
        val apiUrlId = "http://localhost:8080/carBases/$identifier"
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