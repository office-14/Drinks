package com.coffeedose.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coffeedose.R
import kotlinx.android.synthetic.main.activity_main.*

class CoffeeDoseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
