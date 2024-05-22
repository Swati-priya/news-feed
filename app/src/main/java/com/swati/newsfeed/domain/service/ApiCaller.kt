package com.swati.newsfeed.domain.service

import com.google.gson.GsonBuilder
import com.swati.newsfeed.domain.model.NewsListApiResponse
import com.swati.newsfeed.domain.util.Resource
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ApiCaller {

    private fun callApi(urlString: String): String? {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var jsonResponse: String? = null

        try {
            val url = URL(urlString)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            val inputStream = urlConnection.inputStream
            val builder = StringBuilder()
            reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                builder.append(line).append("\n")
            }

            if (builder.isNotEmpty()) {
                jsonResponse = builder.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            reader?.close()
        }

        return jsonResponse
    }

    fun loadArticleUsingSystemService(): Resource<NewsListApiResponse> {
        val url =
            "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

        lateinit var respo: Resource<NewsListApiResponse>
        val response: String?
        try {
            response = callApi(url)
            response?.let {
                val gson = GsonBuilder().create()
                val urlResponse = gson.fromJson(it, NewsListApiResponse::class.java)
                respo = Resource.success(urlResponse)
            } ?: run { respo = Resource.error("Oops! Something Went Wrong") }
        } catch (e: IOException) {
            respo = Resource.error(e.message.toString())
        }
        return respo
    }
}
