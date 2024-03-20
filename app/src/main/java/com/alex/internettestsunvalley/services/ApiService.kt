package com.alex.internettestsunvalley.services
import com.alex.internettestsunvalley.Models.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: 7PAPusdFBo1l6brUQokAl2XjaaBdLtp6RxGZRuBZcEa9SGUuVEXj1YaYPehmGvio", "Content-Type: application/json")
    @GET("getTasksAll")
    fun getTasks(): Call<List<Task>>

    @Headers("Authorization: 7PAPusdFBo1l6brUQokAl2XjaaBdLtp6RxGZRuBZcEa9SGUuVEXj1YaYPehmGvio", "Content-Type: application/json")
    @POST("TasksCreate")
    fun createTask(@Body requestBody: Any): Call<Void>


    @Headers("Authorization: 7PAPusdFBo1l6brUQokAl2XjaaBdLtp6RxGZRuBZcEa9SGUuVEXj1YaYPehmGvio", "Content-Type: application/json")
    @GET("getTasksbyid")
    fun getTasksbyid(@Query("title") title: String): Call<List<Task>> // Asegúrate de reemplazar Task con el tipo de objeto que esperas recibir
}