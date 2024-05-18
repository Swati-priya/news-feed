package com.swati.newsfeed.presentation.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swati.newsfeed.domain.model.Article
import com.swati.newsfeed.domain.repository.ArticleRepository
import com.swati.newsfeed.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListingViewModel @Inject constructor() : ViewModel() {
    private var _articleApiResponseLD: MutableLiveData<ArticleSealedClass> = MutableLiveData()
    val articleApiResponseLD: LiveData<ArticleSealedClass> = _articleApiResponseLD
    fun loadArticles() {
        viewModelScope.launch {
            _articleApiResponseLD.postValue(ArticleSealedClass.Loading)
            ArticleRepository().loadArticles().collectLatest {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.articles?.let { list ->
                            _articleApiResponseLD.postValue(ArticleSealedClass.Success(list.toMutableList()))
                        } ?: run {
                            _articleApiResponseLD.postValue(ArticleSealedClass.ApiError("Oops! Something Went Wrong"))
                        }
                    }

                    Resource.Status.ERROR -> {
                        _articleApiResponseLD.postValue(
                            ArticleSealedClass.ApiError(it.message ?: "Oops! Something Went Wrong"),
                        )
                    }
                }
            }
        }
    }
}

sealed class ArticleSealedClass {
    object Loading : ArticleSealedClass()
    data class ApiError(var errorMsg: String) : ArticleSealedClass()
    data class Success(var articleList: MutableList<Article>) : ArticleSealedClass()
}