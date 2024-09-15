package com.example.merqurinotes.settings.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.ActivitySettingsBinding
import com.example.merqurinotes.settings.viewmodel.SettingsViewModel
import com.example.merqurinotes.utils.api.ApiResource
import com.example.merqurinotes.utils.dialog.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel : SettingsViewModel by lazy { ViewModelProvider(this)[SettingsViewModel::class.java] }
    companion object {
        fun start(
            context: Context,
        ) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }
    private fun initView() {
        val activity = this
        binding.apply {
            //----------------------Custom Action Bar---------------------
            customActionBar.backButton.setOnClickListener {
                finish()
            }
            customActionBar.settingsButton.visibility = View.GONE
            customActionBar.titleText.text = "Settings"
            //------------------------------------------------------------

            //----------------------Container-----------------------------
            onlineCustomerLl.onlineCustomerContainer.setOnClickListener {
                Toast.makeText(activity,"Online Customer Clicked",Toast.LENGTH_SHORT).show()
            }

            userAgreementLl.userAgreementContainer.setOnClickListener {
                Toast.makeText(activity,"User Agreement Clicked",Toast.LENGTH_SHORT).show()
            }

            privatePolicyLl.privatePolicyContainer.setOnClickListener {
                Toast.makeText(activity,"Private Policy Clicked",Toast.LENGTH_SHORT).show()
            }

            aboutUsLl.aboutUsContainer.setOnClickListener {
                Toast.makeText(activity,"About Us Clicked",Toast.LENGTH_SHORT).show()
            }
            //------------------------------------------------------------

            //----------------------Bottom Bar-----------------------------
            customBottomBar.btnSaveDelete.text = "Delete"
            customBottomBar.btnSaveDelete.setOnClickListener {
                viewModel.deleteAllContent()
            }
            //------------------------------------------------------------
        }
    }
    private fun initViewModel() {
        val activity = this
        viewModel.apply {
            retrieveDeleteAllDataResponse.observe(
                activity,
                activity::onRetrieveDeleteAllContentResponse
            )
        }
    }

    private fun onRetrieveDeleteAllContentResponse(response: ApiResource<Boolean>?) {
        val activity = this@SettingsActivity
        when (response) {
            is ApiResource.Loading -> {
                DialogUtils.showLoadingDialog(activity)
            }

            is ApiResource.Success -> {
                DialogUtils.shutDownLoadingDialog()
            }

            is ApiResource.Error -> {
                DialogUtils.shutDownLoadingDialog()
                DialogUtils.showSimpleOkDialog(
                    activity,
                    title = getString(R.string.dialog_title_error),
                    message = getString(R.string.generic_error_msg),
                    positiveButtonText = getString(R.string.dialog_button_ok),
                    positiveButtonAction = {
                        finish()
                    },
                )
            }
            else -> {}
        }
    }

}