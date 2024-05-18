package com.swati.newsfeed.domain.model

import com.google.gson.annotations.SerializedName

data class NewsListApiResponse(
    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("status")
    val status: String?,
)