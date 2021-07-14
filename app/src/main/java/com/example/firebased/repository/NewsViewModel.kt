package com.example.firebased.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebased.model.DataItem
import com.example.firebased.model.Info
import com.example.firebased.viewmodel.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel constructor(application: Application) : AndroidViewModel(application) {

    val newsE = MutableLiveData<DataItem>()
    var items: MutableList<Info> = mutableListOf()
    private val modelInstance = NewsRepository(application)
    private lateinit var news: Call<DataItem>

    fun getData(page: Int): MutableLiveData<DataItem> {
        //val news = NewsClient.getClient(application).getData("in", page)
        this.news = modelInstance.getServicesApiCall(page)
        news.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                val news = response.body()
                if (news != null) {
                    /*val articles = news.articles
                    newsE.value = DataItem(articles)*/
                    items
                    items.addAll(news.articles)
                    val results = news.totalResults
                    newsE.value = DataItem(results, items)
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                /*Toast.makeText(this, "Failed to fetch news.", Toast.LENGTH_SHORT)
                    .show()*/
            }
        })
        return newsE
    }
}