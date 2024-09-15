package com.example.merqurinotes.base

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.merqurinotes.R

abstract class BaseAppCompatActivity : AppCompatActivity() {

    private lateinit var toolbar: ConstraintLayout
    private lateinit var footer: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // Initialize toolbar
        //toolbar = findViewById(R.id.custom_title_bar)
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Initialize footer
        footer = findViewById(R.id.footer_container)
        // You can set up listeners or initialize footer components here if needed
        footer.visibility = ConstraintLayout.VISIBLE
    }

    fun setToolbarTitle(visible: Boolean){
        footer.visibility = if (visible) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
    }

    fun setFooterVisibility(visible: Boolean) {
        footer.visibility = if (visible) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
    }
}