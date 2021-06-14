package com.example.firebased.service.news

import com.example.firebased.service.news.NewsService.Companion.baseURL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsClient {

    val newsCall: NewsService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        newsCall = retrofit.create(NewsService::class.java)
    }
}