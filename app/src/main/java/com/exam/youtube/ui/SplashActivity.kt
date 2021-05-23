package com.exam.youtube.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.exam.youtube.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Залипание на фрагменте с иконком youtube
        Thread.sleep(500)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}