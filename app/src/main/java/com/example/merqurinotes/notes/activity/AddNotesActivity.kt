package com.example.merqurinotes.notes.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.example.merqurinotes.MainActivity
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.ActivityAddNotesBinding
import com.example.merqurinotes.databinding.ActivitySettingsBinding
import com.example.merqurinotes.notes.common.response.RetrieveCategoryListResponse
import com.example.merqurinotes.notes.viewmodel.AddNotesViewModel
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.settings.activity.SettingsActivity
import com.example.merqurinotes.splashscreen.viewmodel.SplashScreenViewModel
import com.example.merqurinotes.utils.api.ApiResource
import com.example.merqurinotes.utils.dialog.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    private val viewModel : AddNotesViewModel by lazy { ViewModelProvider(this)[AddNotesViewModel::class.java] }

    companion object {
        fun start(
            context: Context,
        ) {
            context.startActivity(Intent(context, AddNotesActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        val activity = this
        viewModel.apply {
            retrieveListCategoryResponse.observe(
                activity,
                activity::onRetrieveCategoryListFromAPIResponse
            )
            retrieveSaveNotesResponse.observe(
                activity,
                activity::onRetrieveSaveNotesResponse
            )
        }
    }

    private fun onRetrieveSaveNotesResponse(response: ApiResource<Boolean>?) {
        val activity = this@AddNotesActivity
        when (response) {
            is ApiResource.Loading -> {
                DialogUtils.showLoadingDialog(activity)
            }

            is ApiResource.Success -> {
                DialogUtils.shutDownLoadingDialog()
                if(response.data) Toast.makeText(activity,"Saved successfully",Toast.LENGTH_SHORT).show()
                else{
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

    private fun onRetrieveCategoryListFromAPIResponse(response: ApiResource<RetrieveCategoryListResponse>?) {
        val activity = this@AddNotesActivity
        when (response) {
            is ApiResource.Loading -> {
                DialogUtils.showLoadingDialog(activity)
            }

            is ApiResource.Success -> {
                DialogUtils.shutDownLoadingDialog()
                response.data.apply {
                    updateDropDownList(response.data)
                }
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

    private fun updateDropDownList(data: RetrieveCategoryListResponse) {

    }

    private fun initView() {
        binding.apply {
            toolbar.settingsButton.visibility = View.GONE
            toolbar.titleText.text = "New Notes"
            toolbar.backButton.setOnClickListener { finish() }

            bottomSaveButton.btnSaveDelete.text = "Save"
            bottomSaveButton.btnSaveDelete.setOnClickListener {
                val inputContent = etNoteContent.text.toString()
                val inputCategory = spinnerCategory.selectedItem.toString()
                val content = Content(
                    category = inputCategory,
                    content = inputContent
                )
                viewModel.saveNotesToDB(content)
            }
        }
    }
}