package com.swati.newsfeed.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swati.newsfeed.databinding.ActivityNewsListingBinding

class NewListingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsListingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}
