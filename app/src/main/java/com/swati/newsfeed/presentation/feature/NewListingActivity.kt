package com.swati.newsfeed.presentation.feature

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.swati.newsfeed.R
import com.swati.newsfeed.databinding.ActivityNewsListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListingActivity : AppCompatActivity(), ArticleClickListener {
    private val binding: ActivityNewsListingBinding by lazy {
        ActivityNewsListingBinding.inflate(layoutInflater)
    }

    private val viewmodel by viewModels<NewsListingViewModel>()

    private val newsListAdaptor: NewsListAdaptor by lazy {
        NewsListAdaptor(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolBar()
        binding.newsListingLayout.rvNewsList.adapter = newsListAdaptor
        viewmodel.loadArticles()
        setObserver()
    }

    private fun initToolBar() {
        val toolBar = binding.toolbar.apply {
            // set the title and its color
            this.title = "News Articles"
            this.setTitleTextColor(
                ContextCompat.getColor(
                    this@NewListingActivity,
                    R.color.white,
                ),
            )
        }
        // setting color of overflow icon
        if (toolBar.overflowIcon != null) {
            val overflow = ResourcesCompat.getDrawable(resources, R.drawable.ic_filter, null)
            toolBar.overflowIcon = overflow
        }
        setSupportActionBar(toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.article_filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.old_first -> {
                viewmodel.sortListAsPerMenu(0)
                true
            }

            R.id.latest_first -> {
                viewmodel.sortListAsPerMenu(1)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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

    override fun headerClickListener(url: String?) {
        try {
            url?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } ?: run {
                Toast.makeText(this, "Url is null", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Please install a web-browser",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}
