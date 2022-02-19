package com.example.memorycards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToDifficultyActivity()
    }

    private fun goToDifficultyActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, DifficultyActivity::class.java))
            finish()
        }, 3000)
    }
}