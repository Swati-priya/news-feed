package com.swati.newsfeed.domain.service

import com.google.gson.GsonBuilder
import com.swati.newsfeed.domain.model.NewsListApiResponse
import com.swati.newsfeed.domain.util.Resource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class OkhttpApiService() {
    private val client = OkHttpClient()

    private val url =
        "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

    fun loadArticles(): Resource<NewsListApiResponse> {
        val request: Request = Request.Builder()
            .url(url)
            .build()

        lateinit var respo: Resource<NewsListApiResponse>
        val response: Response
        try {
            response = client.newCall(request).execute()
            if (response.body != null) {
                val result = response.body?.string()
                val gson = GsonBuilder().create()
                respo = if (response.isSuccessful) {
                    val urlResponse = gson.fromJson(result, NewsListApiResponse::class.java)
                    Resource.success(urlResponse)
                } else {
                    Resource.error("Oops! Something Went Wrong")
                }
            } else {
                respo = Resource.error("Oops! Something Went Wrong")
            }
        } catch (e: IOException) {
            respo = Resource.error(e.message.toString())
        }
        return respo
    }
}
