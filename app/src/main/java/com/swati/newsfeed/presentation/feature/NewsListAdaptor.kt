package com.swati.newsfeed.presentation.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swati.newsfeed.databinding.NewsListItemBinding
import com.swati.newsfeed.domain.model.Article

class NewsListAdaptor(private val listener: ArticleClickListener) :
    RecyclerView.Adapter<NewsListItemViewHolder>() {
    private var articleList: MutableList<Article> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItemViewHolder {
        return NewsListItemViewHolder(
            binding = NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener = listener,
        )
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: NewsListItemViewHolder, position: Int) {
        articleList.getOrNull(position)?.let {
            holder.setData(it)
        }
    }

    fun setNewsListData(articleList: MutableList<Article>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }
}
