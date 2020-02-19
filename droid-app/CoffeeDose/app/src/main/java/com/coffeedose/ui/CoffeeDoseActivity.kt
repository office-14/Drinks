package com.coffeedose.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coffeedose.R

class CoffeeDoseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
