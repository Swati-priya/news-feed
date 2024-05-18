package com.swati.newsfeed.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.swati.newsfeed.R
import com.swati.newsfeed.databinding.ActivitySplashBinding
import com.swati.newsfeed.presentation.feature.NewListingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(R.drawable.splash).into(binding.ivSplashPage)
            .waitForLayout()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, NewListingActivity::class.java))
            this.finish()
        }, 3000)
    }
}
