package com.example.back.service.carBase
import com.example.back.service.car.Car
import jakarta.persistence.*

@Entity
data class CarBase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val active: Boolean,
    val address: String,

    @OneToMany(mappedBy = "base", cascade = [CascadeType.ALL], orphanRemoval = true)
    val cars: List<Car> = mutableListOf()
)

/*
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
<dependency>
          <groupId>com.example</groupId>
          <artifactId>back</artifactId>
          <version>0.0.1-SNAPSHOT</version>
          <scope>compile</scope>
      </dependency>
      <dependency>
          <groupId>org.apache.httpcomponents</groupId>
          <artifactId>httpclient</artifactId>
          <version>4.5.14</version>
      </dependency>
      <dependency>
          <groupId>com.google.code.gson</groupId>
          <artifactId>gson</artifactId>
          <version>2.10.1</version>
      </dependency>

 */

