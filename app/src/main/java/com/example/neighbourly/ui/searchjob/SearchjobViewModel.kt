package com.example.neighbourly.ui.searchjob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchjobViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is searchjob Fragment"
    }
    val text: LiveData<String> = _text
}