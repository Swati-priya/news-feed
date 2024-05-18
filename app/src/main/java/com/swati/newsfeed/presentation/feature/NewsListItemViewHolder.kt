package com.swati.newsfeed.presentation.feature

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.swati.newsfeed.R
import com.swati.newsfeed.databinding.NewsListItemBinding
import com.swati.newsfeed.domain.model.Article
import com.swati.newsfeed.presentation.util.TimeUtl

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
            tvPublishedAt.text = TimeUtl.utcToLocal(
                dateFormatInput = "yyyy-MM-dd'T'HH:mm:ss'Z'",
                dateFormatOutput = "d MMM yyyy h:mm a",
                datesToConvert = data.publishedAt,
            )
            tvNewsHeadline.setOnClickListener {
                listener.headerClickListener(data.url)
            }
        }
    }
}
