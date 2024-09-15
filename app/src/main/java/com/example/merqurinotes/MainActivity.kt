package com.example.merqurinotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.merqurinotes.base.BaseAppCompatActivity
import com.example.merqurinotes.room.AppDatabase
import com.example.merqurinotes.databinding.ActivityMainBinding
import com.example.merqurinotes.databinding.ActivitySettingsBinding
import com.example.merqurinotes.databinding.ActivitySplashScreenBinding
import com.example.merqurinotes.notes.activity.AddNotesActivity
import com.example.merqurinotes.settings.activity.SettingsActivity

class MainActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        fun start(
            context: Context,
        ) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val activity = this
        binding.apply {
            customToolBar.settingsButton.setOnClickListener {
                SettingsActivity.start(activity)
            }

            customBottomBar.addNotesContainer.setOnClickListener {
                AddNotesActivity.start(activity)
            }

            customToolBar.backButton.visibility = View.GONE
            customToolBar.titleText.text = "Home"
        }
    }
}