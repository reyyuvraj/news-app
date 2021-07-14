package com.example.firebased.viewmodel

import android.app.Application
import com.example.firebased.model.DataItem
import com.example.firebased.service.news.NewsClient
import retrofit2.Call

class NewsRepository constructor(val application: Application) {

    fun getServicesApiCall(pageNum: Int): Call<DataItem> {
        return NewsClient.getClient(application).getData("in",1)
    }
}