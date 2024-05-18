package com.swati.newsfeed.presentation.feature

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
            hideShimmer()
            when (it) {
                ArticleSealedClass.Loading -> {
                    showShimmer()
                }

                is ArticleSealedClass.ApiError -> {
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                }

                is ArticleSealedClass.Success -> {
                    newsListAdaptor.setNewsListData(it.articleList)
                }
            }
        }
    }

    private fun showShimmer() {
        binding.newsListingLayout.apply {
            rvNewsList.isVisible = false
            shimmerArticles.startShimmer()
            shimmerArticles.isVisible = true
        }
    }

    private fun hideShimmer() {
        binding.newsListingLayout.apply {
            rvNewsList.isVisible = true
            shimmerArticles.stopShimmer()
            shimmerArticles.isVisible = false
        }
    }
}
