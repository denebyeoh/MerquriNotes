package com.example.merqurinotes.splashscreen.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.merqurinotes.MainActivity
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.ActivityMainBinding
import com.example.merqurinotes.databinding.ActivitySplashScreenBinding
import com.example.merqurinotes.splashscreen.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel : SplashScreenViewModel by lazy { ViewModelProvider(this)[SplashScreenViewModel::class.java] }
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_splash_screen)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        viewModel.fetchTestData()
    }

    private fun initViewModel() {
        val activity = this
        viewModel.apply {
            retrieveTestResponse.observe(
                activity,
                activity::onRetrieveTestResponse
            )
        }
    }

    private fun onRetrieveTestResponse(b: Boolean?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}