package com.alex.internettestsunvalley.controllers

import android.content.Context
import android.view.View
import android.widget.Toast
import com.alex.internettestsunvalley.Models.Task
import com.alex.internettestsunvalley.callBacks.TaskCallback
import com.alex.internettestsunvalley.services.ApiClient
import com.alex.internettestsunvalley.services.ConectionI.macDevice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object ControllerData {


    fun processDataForSendDataToApi(context: Context,resposeConection:Boolean): Boolean {
        var responseMac=macDevice(context)
        var estado="on"
        if (responseMac=="WiFi está deshabilitado"){
            estado="off"
        }
        return createTask(resposeConection,responseMac,estado)
    }




   private fun createTask(conection:Boolean,mac:String?, estado:String?): Boolean {

        val task=Task(
            title = mac,
            description = conection.toString(),
            estado = estado
        )
        var responseTask=true
        val call = ApiClient.apiService.createTask(task)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code()!=201){
                    responseTask=false
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                responseTask=false
            }
        })
        return responseTask
    }


    fun getDataAllTaskById(view: View, callback: TaskCallback) {
        var responseMac = macDevice(view.context)
        val call = ApiClient.apiService.getTasksbyid(responseMac.toString())
        call.enqueue(object : Callback<List<Task>> {

            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    if (tasks != null) {
                        callback.onSuccess(tasks)
                    } else {
                        callback.onFailure(Exception("La lista de tareas está vacía"))
                    }
                } else {
                    callback.onFailure(Exception("Error al obtener las tareas"))
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }


}