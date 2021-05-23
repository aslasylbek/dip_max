package com.exam.youtube.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.exam.youtube.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onSupportNavigateUp(): Boolean = try {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    } catch (e: Exception) {
        false
    }
}