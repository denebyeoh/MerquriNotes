package com.example.merqurinotes.settings.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
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
    private val viewModel: SettingsViewModel by lazy { ViewModelProvider(this)[SettingsViewModel::class.java] }

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
            customActionBar.titleText.text = getString(R.string.title_settings_tool_bar)
            //------------------------------------------------------------

            //----------------------Container-----------------------------
            onlineCustomerLl.onlineCustomerContainer.setOnClickListener {
                intentToWeb("https://www.merquri.io/contact")
            }

            userAgreementLl.userAgreementContainer.setOnClickListener {
                intentToWeb("https://www.merquri.io/services/app")
            }

            privatePolicyLl.privatePolicyContainer.setOnClickListener {
                intentToWeb("https://www.merquri.io/")
            }

            aboutUsLl.aboutUsContainer.setOnClickListener {
                intentToWeb("https://www.merquri.io/about/us")
            }
            //------------------------------------------------------------

            //----------------------Bottom Bar-----------------------------
            customBottomBar.btnSaveDelete.text = getString(R.string.delete)
            customBottomBar.btnSaveDelete.setOnClickListener {
                DialogUtils.showNormalDialog(
                    activity,
                    title = getString(R.string.dialog_title_note),
                    message = getString(R.string.delete_all_content_question),
                    positiveButtonText = getString(R.string.dialog_button_ok),
                    positiveButtonAction = {
                        viewModel.deleteAllContent()
                    },
                    negativeButtonText = getString(R.string.dialog_button_cancel),
                    negativeButtonAction = {
                    }
                )
            }
            //------------------------------------------------------------
        }
    }

    fun intentToWeb(url:String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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
                DialogUtils.showSimpleOkDialog(
                    activity,
                    title = getString(R.string.dialog_title_note),
                    message = getString(R.string.notes_cleared_statement),
                    positiveButtonText = getString(R.string.dialog_button_ok),
                    positiveButtonAction = {
                    },
                )
            }

            is ApiResource.Error -> {
                DialogUtils.shutDownLoadingDialog()
                DialogUtils.showSimpleOkDialog(
                    activity,
                    title = getString(R.string.dialog_title_error),
                    message = getString(R.string.generic_error_msg),
                    positiveButtonText = getString(R.string.dialog_button_ok),
                    positiveButtonAction = {
                    },
                )
            }

            else -> {}
        }
    }

}