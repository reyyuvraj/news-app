package com.example.firebased.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.firebased.model.DataItem
import com.example.firebased.repository.NewsRepository

class NewsViewModel constructor(application: Application) : AndroidViewModel(application) {
    val response = NewsRepository(application)
    var news: LiveData<DataItem> = response.getData()
}