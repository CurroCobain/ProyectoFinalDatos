package com.example.front

import com.example.front.clases.CarBaseDTO
import com.example.front.clases.CarDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

/**
 * Controlador para la gestión de información sobre bases de taxis y taxis.
 */
class HelloController {
    // URLs de la API
    private val apiUrlCarBases = "http://localhost:8080/carBases/"
    private val apiUrlCars = "http://localhost:8080/cars/"

    // Cliente HTTP y objeto Gson para manejar JSON
    private val httpClient = HttpClient.newHttpClient()
    private val gson = Gson()

    // Componentes de la interfaz de usuario
    @FXML
    private lateinit var infoListViewBases: ListView<CarBaseDTO>
    @FXML
    private lateinit var infoListViewTaxis: ListView<CarDTO>
    @FXML
    private lateinit var textField2Bases: TextField
    @FXML
    private lateinit var textField3Bases: TextField
    @FXML
    private lateinit var textField4Bases: TextField
    @FXML
    private lateinit var textField2Taxis: TextField
    @FXML
    private lateinit var textField3Taxis: TextField
    @FXML
    private lateinit var textField4Taxis: TextField
    @FXML
    private lateinit var infoTextFieldGeneral: TextField


    // ------------------------------------ Gestión de bases ----------------------------------------

    /**
     * Método invocado al hacer clic en el botón de actualización de bases.
     * Actualiza la base de taxis seleccionada.
     */
    @FXML
    private fun onUpdateButtonClickBases() {
        // Obtiene el ID de la base seleccionada
        val id = infoListViewBases.selectionModel.selectedItem.id
        val url = apiUrlCarBases + id // Construye la URL con el ID del recurso que se va a actualizar
        // Crea un objeto CarBaseDTO con los datos actualizados
        val updatedCarBase = CarBaseDTO(
            id,
            textField2Bases.text,
            totrue(textField3Bases.text),
            textField4Bases.text
        )
        // Realiza la solicitud de actualización y actualiza la interfaz de usuario
        infoListViewBases.items.clear()
        infoTextFieldGeneral.text = updateCarBaseRequest(url, updatedCarBase)
        onSearchButtonClickBases()
    }
    /**
     * Método invocado al hacer clic en el botón de eliminación de bases.
     * Elimina la base de taxis seleccionada.
     */
    @FXML
    private fun onDeleteButtonClickBases() {
       val id = infoListViewBases.selectionModel.selectedItem.id
        val url = apiUrlCarBases + id // Agrega el ID al final de la URL
        infoListViewBases.items.clear()
        infoTextFieldGeneral.text = deleteRequest(url)
        onSearchButtonClickBases()
    }

    /**
     * Método invocado al hacer clic en el botón de crear  base.
     * Agrega una nueva base de taxis.
     */
    @FXML
    private fun onAddButtonClickBases() {
        // Crea un nuevo objeto CarBaseDTO con los datos ingresados
        val myCarbase = CarBaseDTO(
            null,
            textField2Bases.text,
            totrue(textField3Bases.text),
            textField4Bases.text
        )
        // Realiza la solicitud de crear y actualiza la interfaz de usuario
        val url = apiUrlCarBases+"add"
        infoListViewBases.items.clear()
        infoTextFieldGeneral.text= sendPostCarBaseRequest(url, myCarbase)
        onSearchButtonClickBases()

    }

    /**
     * Método invocado al hacer clic en el botón de búsqueda de bases.
     * Obtiene y muestra todas las bases de taxis disponibles.
     */
    @FXML
    private fun onSearchButtonClickBases(){
        val url = apiUrlCarBases+"allBases"
        val response = sendGetRequest(url)
        val carBaseList = parseJsonToCarBasesDTOList(response)
        infoListViewBases.items.clear()
        infoListViewBases.items.addAll(carBaseList)
    }

    // ------------------------------------ Gestión de taxis ----------------------------------------

    /**
     * Método invocado al hacer clic en el botón de actualización de taxis.
     * Actualiza el taxi seleccionado.
     */
    @FXML
    private fun onUpdateButtonClickTaxis() {
        val id = infoListViewTaxis.selectionModel.selectedItem.id
        val url = apiUrlCars + id
        val carBase = getCarBaseById(textField4Taxis.text.toLong())

        // Verifica si la CarBase existe
        if (carBase != null) {
            val updatedCar = CarDTO(
                id,
                textField2Taxis.text,
                totrue(textField3Taxis.text),
                carBase
            )
            infoListViewTaxis.items.clear()
            infoTextFieldGeneral.text = updateCarRequest(url, updatedCar)
            onSearchButtonClickBases()
        } else {
            infoTextFieldGeneral.text = "La base de taxis no existe."
        }
    }
    /**
     * Método invocado al hacer clic en el botón de eliminación de taxis.
     * Elimina el taxi seleccionado.
     */
    @FXML
    private fun onDeleteButtonClickTaxis() {
        val id = infoListViewTaxis.selectionModel.selectedItem.id
        val url = apiUrlCars + id // Agrega el ID al final de la URL
        infoListViewTaxis.items.clear()
        infoTextFieldGeneral.text = deleteRequest(url)
        onSearchButtonClickBases()
    }

    /**
     * Método invocado al hacer clic en el botón de crear  taxi.
     * Agrega una nueva base de taxis.
     */
    @FXML
    private fun onAddButtonClickTaxis() {
        val carBase = getCarBaseById(textField4Taxis.text.toLong())

        // Verifica si la CarBase existe
        if (carBase != null) {
            val myCar = CarDTO(
                null,
                textField2Taxis.text,
                totrue(textField3Taxis.text),
                carBase
            )
            val url = apiUrlCars+"add"
            infoListViewBases.items.clear()
            infoTextFieldGeneral.text= sendPostCarRequest(url, myCar)
            onSearchButtonClickBases()
        } else {
            infoTextFieldGeneral.text = "La base de taxis no existe."
        }
    }
    /**
     * Método invocado al hacer clic en el botón de búsqueda de taxis.
     * Obtiene y muestra todos los taxis disponibles.
     */
    @FXML
    private fun onSearchButtonClickTaxis(){
        val url = apiUrlCars+"allCars"
        val response = sendGetRequest(url)
        val carList = parseJsonToCarDTOList(response)
        infoListViewTaxis.items.clear()
        infoListViewTaxis.items.addAll(carList)

    }

    // ---------------  Funciones para realizar solicitudes HTTP y manejar las respuestas ---------------------------

    /**
     * Envía una solicitud Http de tipo POST  para objetos CarBaseDTO y maneja la respuesta
     */
    fun sendPostCarBaseRequest(url: String, carBaseDTO: CarBaseDTO): String {
        //Parsea el objeto recibido a json
        val requestBody = gson.toJson(carBaseDTO)

        // Crea una solicitud de tipo POST
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        // Se envía la solicitud y se gestiona la respuesta
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        // Devuelve la respuesta de la solicitud
        return responseBody
    }

    /**
     * Envía una solicitud Http de tipo PUT para objetos CarBaseDTO  y maneja la respuesta
     */
    private fun updateCarBaseRequest(url: String, carBaseDTO: CarBaseDTO): String {
        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(carBaseDTO)

        // Crea una solicitud de tipo PUT
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        // Se envía la solicitud y se gestiona la respuesta
        val httpClient = HttpClient.newHttpClient()
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        // Devuelve la respuesta de la solicitud
        return responseBody
    }
    /**
     * Envía una solicitud Http de tipo PUT para objetos CarDTO  y maneja la respuesta
     */
    private fun updateCarRequest(url: String, carDTO: CarDTO): String {
        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(carDTO)

        // Crea una solicitud de tipo PUT
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        // Se envía la solicitud y se gestiona la respuesta
        val httpClient = HttpClient.newHttpClient()
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        // Devuelve la respuesta de la solicitud
        return responseBody
    }
    /**
     * Envía una solicitud Http de tipo POST  para objetos CarDTO y maneja la respuesta
     */
    private fun sendPostCarRequest(url: String, carDTO: CarDTO): String{
        //Parsea el objeto recibido a json
        val requestBody = gson.toJson(carDTO)

        // Crea una solicitud de tipo POST
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        // Se envía la solicitud y se gestiona la respuesta
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        // Devuelve la respuesta de la solicitud
        return responseBody
    }
    /**
     * Envía una solicitud Http de tipo GET y maneja la respuesta
     */
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
    /**
     * Envía una solicitud Http de tipo DELETE y maneja la respuesta
     */
    fun deleteRequest(url: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .DELETE()
            .build()

        val httpClient = HttpClient.newHttpClient()
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        val responseBody = httpResponse.body()

        return responseBody
    }


    // ----------------------------------- parsers ------------------------------------
    /**
     * Parsea información en formato json a una lista de objetos de tipo CarBaseDTO
     * @see CarBaseDTO
     */
    private fun parseJsonToCarBasesDTOList(json: String): List<CarBaseDTO> {
        val carBasesList = object : TypeToken<List<CarBaseDTO>>() {}.type
        return gson.fromJson(json, carBasesList)
    }

    /**
     * Parsea información en formato json a objetos de tipo CarBaseDTO
     * @see CarBaseDTO
     */
    private fun parseJsonToCarBaseDTO(json: String): CarBaseDTO {
        val carBasesDTO = object : TypeToken<CarBaseDTO>() {}.type
        return gson.fromJson(json, carBasesDTO)
    }

    /**
     * Parsea información en formato json a una lista de objetos de tipo CarDTO
     * @see CarDTO
     */
    private fun parseJsonToCarDTOList(json: String): List<CarDTO> {
        val carList = object : TypeToken<List<CarDTO>>() {}.type
        return gson.fromJson(json, carList)
    }

    /**
     * Parsea información en formato json a objetos de tipo CarDTO
     * @see CarDTO
     */
    private fun parseJsonToCarDTO(json: String): CarDTO {
        val carDTO = object : TypeToken<CarDTO>() {}.type
        return gson.fromJson(json, carDTO)
    }

    // --------------------- funciones adicionales para la lógica de la aplicación  ------------------------------

    /**
     * Obtiene una CarBase determinada de la base de datos
     */
    private fun getCarBaseById(identifier: Long): CarBaseDTO? {
        val apiUrlId = "http://localhost:8080/carBases/$identifier"
        val response = sendGetRequest(apiUrlId)
        // Verifica si la respuesta es un error o está vacía
        if (response.isEmpty() || response.contains("error")) {
            return  null
        }
        val carBase1 = parseJsonToCarBaseDTO(response)
        return carBase1
    }

    /**
     * Obtiene una taxi determinado de la base de datos
     */
    private fun getCarById(identifier: Long): CarDTO {
        val apiUrlId = "http://localhost:8080/cars/$identifier"
        val response = sendGetRequest(apiUrlId)
        val car1 = parseJsonToCarDTO(response)
        return car1
    }

    /**
     * Función que pasa texto a Booleano
     */
    fun totrue(string: String): Boolean{
        if (string.lowercase(Locale.getDefault()) == "true"){
            return true
        }else if(string.lowercase(Locale.getDefault()) == "false"){
            return  false
        }
        return false
    }

    /**
     * Método para manejar el evento de doble clic en las listas
     */
    @FXML
    fun handleListViewClick(event: MouseEvent) {
        if (event.button == MouseButton.PRIMARY && event.clickCount == 2) {
            val listView = event.source as ListView<*>
            val selectedItem = listView.selectionModel.selectedItem

            // Verifica en qué lista se hizo clic y muestra la información correspondiente en los campos de texto
            if (listView == infoListViewTaxis) {

                val taxiInfo = selectedItem as CarDTO

                // Muestra la información en los campos de texto correspondientes
                textField2Taxis.text = taxiInfo.plate
                textField3Taxis.text = taxiInfo.available.toString()
                textField4Taxis.text = taxiInfo.base?.id.toString()
                val myBase = getCarBaseById(textField4Taxis.text.toLong())
                textField2Bases.text = myBase!!.name
                textField3Bases.text = myBase.active.toString()
                textField4Bases.text = myBase.address
                infoListViewBases.items.clear()
                infoListViewBases.items.addAll(myBase)

            } else if (listView == infoListViewBases) {
                val baseInfo = selectedItem as CarBaseDTO

                // Muestra la información en los campos de texto correspondientes
                textField2Bases.text = baseInfo.name
                textField3Bases.text = baseInfo.active.toString()
                textField4Bases.text = baseInfo.address
                infoListViewTaxis.items.clear()
                infoListViewTaxis.items.addAll(filtrarTaxis(baseInfo.id!!.toLong()))
            }
        }
    }

    /**
     * Obtiene todos los taxis y los filtra en base a un parámetro concreto
     * @return devuelve una lista con los taxis filtrados por dicho parámetro
     */
    private fun filtrarTaxis(id: Long): MutableList<CarDTO> {
        // obtenemos la lista de taxis completa
        val nuevaLista: MutableList<CarDTO> = mutableListOf()
        val url = apiUrlCars+"allCars"
        val response = sendGetRequest(url)
        val carList = parseJsonToCarDTOList(response)

        // Se filtran los taxis y los añadimos a la nueva lista
        for (i in carList){
            if(i.base?.id == id){
                nuevaLista.add(i)
            }
        }
        // Devuelve la nueva lista filtrada
        return nuevaLista
    }
    @FXML
    private fun onDeleteResultsButtonClick(){
        textField2Bases.text = ""
        textField3Bases.text = ""
        textField4Bases.text = ""
        textField2Taxis.text = ""
        textField3Taxis.text = ""
        textField4Taxis.text = ""
        infoTextFieldGeneral.text = ""
    }

}