package com.swati.newsfeed.presentation.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.swati.newsfeed.databinding.ActivityNewsListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListingActivity : AppCompatActivity() {
    private val binding: ActivityNewsListingBinding by lazy {
        ActivityNewsListingBinding.inflate(layoutInflater)
    }

    private val viewmodel by viewModels<NewsListingViewModel>()

    private val newsListAdaptor: NewsListAdaptor by lazy {
        NewsListAdaptor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.newsListingLayout.rvNewsList.adapter = newsListAdaptor
        viewmodel.loadArticles()
        setObserver()
    }

    private fun setObserver() {
        viewmodel.articleApiResponseLD.observe(this) {
            when (it) {
                is ArticleSealedClass.ApiError -> {
                }

                is ArticleSealedClass.Success -> {
                    newsListAdaptor.setNewsListData(it.articleList)
                }
            }
        }
    }
}
