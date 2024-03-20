package com.alex.internettestsunvalley.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome TEAM Sun"
    }
    val text: LiveData<String> = _text

    private val _botton = MutableLiveData<String>().apply {
        value = "Save Conection off Internet"
    }
    val botton: LiveData<String> = _botton
}