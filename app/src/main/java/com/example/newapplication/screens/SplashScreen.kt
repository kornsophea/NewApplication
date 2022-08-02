package com.example.newapplication.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newapplication.MainActivity
import com.example.newapplication.R
import com.example.newapplication.fragment.HomeFragment

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(
            {
               startActivity(Intent(this,LoginScreen::class.java))
            }
        ,2000
        )
    }
}