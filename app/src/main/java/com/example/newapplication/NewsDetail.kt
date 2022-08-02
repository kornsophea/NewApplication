package com.example.newapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newapplication.databinding.ActivityNewsDetailBinding
import com.squareup.picasso.Picasso

class NewsDetail : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgUrl = intent.getStringExtra("imageUrl")
        val content = intent.getStringExtra("content")

        Picasso.get().load(imgUrl).into(binding.imgDetail)
        binding.textDetail.text = content
    }
}