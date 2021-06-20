package com.example.firebased.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.firebased.model.DataItem
import com.example.firebased.service.news.NewsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository constructor(val application: Application) {

    val newsE = MutableLiveData<DataItem>()
    fun getData(): MutableLiveData<DataItem> {
        val news = NewsClient.getClient(application).getData("in", 1)
        news.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                val news = response.body()
                if (news != null) {/*
                    adapter = NewsAdapter(
                        context!!, news.articles,
                        this@NewsFragment*/
                    val articles = news.articles
                    newsE.value = DataItem(articles)
                    /*val recyclerView: RecyclerView = view.findViewById(R.id.newsView)
                    progressbar.visibility = View.GONE
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)*/
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                Toast.makeText(application, "Failed to fetch news.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        return newsE
    }
}