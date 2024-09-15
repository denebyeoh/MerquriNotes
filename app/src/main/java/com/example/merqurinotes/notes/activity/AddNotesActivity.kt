package com.example.merqurinotes.notes.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.ActivityAddNotesBinding
import com.example.merqurinotes.notes.viewmodel.AddNotesViewModel
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.utils.api.ApiResource
import com.example.merqurinotes.utils.dialog.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    private val viewModel: AddNotesViewModel by lazy { ViewModelProvider(this)[AddNotesViewModel::class.java] }

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
                DialogUtils.showSimpleOkDialog(
                    activity,
                    title = getString(R.string.dialog_title_note),
                    message = getString(R.string.content_saved_successful_statement),
                    positiveButtonText = getString(R.string.dialog_button_ok),
                    positiveButtonAction = {
                        binding.noteContentEt.text.clear()
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
                        finish()
                    },
                )
            }
            else -> {}
        }
    }

    private fun initView() {
        binding.apply {
            toolbar.settingsButton.visibility = View.GONE
            toolbar.titleText.text = getString(R.string.title_add_notes_tool_bar)
            toolbar.backButton.setOnClickListener { finish() }

            bottomSaveButton.btnSaveDelete.text = getString(R.string.save)
            bottomSaveButton.btnSaveDelete.setOnClickListener {
                if (noteContentEt.text.toString()
                        .isBlank() || spinnerCategory.selectedItem.toString().isBlank()
                ) {
                    DialogUtils.showSimpleOkDialog(
                        this@AddNotesActivity,
                        title = getString(R.string.dialog_title_error),
                        message = getString(R.string.empty_content),
                        positiveButtonText = getString(R.string.dialog_button_ok),
                        positiveButtonAction = {
                        },
                    )
                } else {
                    val inputContent = noteContentEt.text.toString()
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
}