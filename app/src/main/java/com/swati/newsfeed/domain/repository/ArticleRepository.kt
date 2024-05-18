package com.swati.newsfeed.domain.repository

import com.swati.newsfeed.domain.model.NewsListApiResponse
import com.swati.newsfeed.domain.service.OkhttpApiService
import com.swati.newsfeed.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArticleRepository() {
    fun loadArticles(): Flow<Resource<NewsListApiResponse>> {
        return flow {
            emit(OkhttpApiService().loadArticles())
        }.flowOn(Dispatchers.IO)
    }
}