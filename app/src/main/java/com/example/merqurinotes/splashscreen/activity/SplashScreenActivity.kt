package com.example.merqurinotes.splashscreen.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.merqurinotes.MainActivity
import com.example.merqurinotes.R
import com.example.merqurinotes.room.Category
import com.example.merqurinotes.databinding.ActivitySplashScreenBinding
import com.example.merqurinotes.notes.activity.AddNotesActivity
import com.example.merqurinotes.notes.viewmodel.AddNotesViewModel
import com.example.merqurinotes.splashscreen.viewmodel.SplashScreenViewModel
import com.example.merqurinotes.utils.api.ApiResource
import com.example.merqurinotes.utils.dialog.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel : SplashScreenViewModel by lazy { ViewModelProvider(this)[SplashScreenViewModel::class.java] }
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        //viewModel.fetchDataFromAPI()
        viewModel.fetchCategoryListFromDB()
    }


    private fun initViewModel() {
        val activity = this
        viewModel.apply {
            retrieveDataResponse.observe(
                activity,
                activity::onRetrieveDataFromAPIResponse
            )
            retrieveCategoryListResponse.observe(
                activity,
                activity::onRetrieveCategoryFromDBResponse
            )
            retrieveInsertCategoryToDBResponse.observe(
                activity,
                activity::onRetrieveInsertCategoryResponse
            )
        }
    }

    private fun onRetrieveInsertCategoryResponse(response: ApiResource<Boolean>?) {
        val activity = this@SplashScreenActivity
        when (response) {
            is ApiResource.Loading -> {
                binding.statusText.text = "Loading..."
            }

            is ApiResource.Success -> {
                binding.statusText.text = "Load Completed"
                if(response.data){
                    MainActivity.start(activity)
                    finish()
                }
            }

            is ApiResource.Error -> {
                binding.statusText.text = "Error"
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

    private fun onRetrieveCategoryFromDBResponse(response: ApiResource<List<Category>>?) {
        val activity = this@SplashScreenActivity
        when (response) {
            is ApiResource.Loading -> {
                binding.statusText.text = "Loading..."
            }

            is ApiResource.Success -> {
                //DialogUtils.shutDownLoadingDialog()
                if(response.data.isEmpty()){
                    viewModel.insertCategoryToDB()
                }else{
                    binding.statusText.text = "Load Completed"
                    MainActivity.start(activity)
                    //AddNotesActivity.start(activity)
                    finish()
                }
            }

            is ApiResource.Error -> {
                binding.statusText.text = "Error"
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

    private fun onRetrieveDataFromAPIResponse(response: ApiResource<Boolean>?) {
        val activity = this@SplashScreenActivity
        when (response) {
            is ApiResource.Loading -> {
                //DialogUtils.showLoadingDialog(activity)
                binding.statusText.text = "Loading..."
            }

            is ApiResource.Success -> {
                //DialogUtils.shutDownLoadingDialog()
                binding.statusText.text = "Load Completed"
                MainActivity.start(activity)
                finish()
            }

            is ApiResource.Error -> {
                binding.statusText.text = "Error"
                //DialogUtils.shutDownLoadingDialog()
//                NewDialogUtils.popSimpleOkayDialog(
//                    activity,
//                    DialogHandler.TEXT_NOTICE_ZH,
//                    getString(R.string.generic_error)
//                )
//                DialogUtils.showSimpleOkDialog(
//                    activity,
//                    title = "Warning",
//                    message = "Are you sure you want to delete this?",
//                    positiveButtonText = "Yes",
//                    negativeButtonText = "No",
//                    positiveButtonAction = {
//                        // Positive action (e.g., delete an item)
//                    },
//                    negativeButtonAction = {
//                        // Negative action (e.g., cancel)
//                    }
//                )
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