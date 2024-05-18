package com.swati.newsfeed.presentation.feature

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.swati.newsfeed.R
import com.swati.newsfeed.databinding.NewsListItemBinding
import com.swati.newsfeed.domain.model.Article

class NewsListItemViewHolder(val binding: NewsListItemBinding, val listener: ArticleClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(data: Article) {
        binding.apply {
            Glide.with(itemView.context).load(data.urlToImage).apply(
                RequestOptions().placeholder(R.drawable.ic_placeholder_generic)
                    .error(R.drawable.ic_placeholder_generic),
            ).into(ivNewsImage)
            tvNewsHeadline.text = data.title
            tvNewsContent.text =
                if (data.content.isNullOrBlank()) data.description else data.content
            tvAuthorName.text = data.author
            tvPublishedAt.text = data.publishedAt
            tvNewsHeadline.setOnClickListener {
                listener.headerClickListener(data.url)
            }
        }
    }
}
