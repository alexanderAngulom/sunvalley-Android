package com.alex.internettestsunvalley.callBacks

import com.alex.internettestsunvalley.Models.Task

interface TaskCallback {
    fun onSuccess(tasks: List<Task>)
    fun onFailure(error: Throwable)
}