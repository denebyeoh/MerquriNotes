package com.example.merqurinotes.main.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.FragmentHomeBinding
import com.example.merqurinotes.main.adapter.HealthListAdapter
import com.example.merqurinotes.main.adapter.LifeListAdapter
import com.example.merqurinotes.main.adapter.WorkStudyListAdapter
import com.example.merqurinotes.main.viewmodel.MainViewModel
import com.example.merqurinotes.notes.activity.AddNotesActivity
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.settings.activity.SettingsActivity
import com.example.merqurinotes.utils.api.ApiResource
import com.example.merqurinotes.utils.dialog.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private lateinit var homeBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
        //return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            fetchWorkStudyContentFromDB()
            fetchLifeContentFromDB()
            fetchHealthContentFromDB()
        }
    }

    private fun initView() {
        homeBinding.apply {
            customToolBar.titleText.text = getString(R.string.home)
            customToolBar.backButton.visibility = View.GONE
            customToolBar.settingsButton.setOnClickListener {
                SettingsActivity.start(requireActivity())
            }

            customBottomBar.homeLabel.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.pink
                )
            )
            customBottomBar.summaryContainer.setOnClickListener {
                Navigation.findNavController(homeBinding.root)
                    .navigate(R.id.action_homeFragment_to_summaryFragment)
            }
            customBottomBar.addNotesContainer.setOnClickListener {
                AddNotesActivity.start(requireActivity())
            }
            healthRecyclerView.isNestedScrollingEnabled = false
        }
    }

    private fun initViewModel() {
        val activity = this@HomeFragment
        viewModel.apply {
            retrieveWorkStudyListResponse.observe(
                requireActivity(),
                activity::onRetrieveWorkStudyFromDBResponse
            )
            retrieveLifeListResponse.observe(
                requireActivity(),
                activity::onRetrieveLifeFromDBResponse
            )
            retrieveHealthListResponse.observe(
                requireActivity(),
                activity::onRetrieveHealthFromDBResponse
            )
        }
    }

    private fun onRetrieveHealthFromDBResponse(response: ApiResource<List<Content>>?) {
        when (response) {
            is ApiResource.Loading -> {

            }

            is ApiResource.Success -> {
                updateHealthAdapter(response.data)
            }

            is ApiResource.Error -> {
                DialogUtils.showSimpleOkDialog(
                    requireActivity(),
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

    private fun onRetrieveLifeFromDBResponse(response: ApiResource<List<Content>>?) {
        when (response) {
            is ApiResource.Loading -> {

            }

            is ApiResource.Success -> {
                updateLifeAdapter(response.data)
            }

            is ApiResource.Error -> {
                DialogUtils.showSimpleOkDialog(
                    requireActivity(),
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

    private fun onRetrieveWorkStudyFromDBResponse(response: ApiResource<List<Content>>?) {
        when (response) {
            is ApiResource.Loading -> {

            }

            is ApiResource.Success -> {
                updateWorkStudyAdapter(response.data)
            }

            is ApiResource.Error -> {
                DialogUtils.showSimpleOkDialog(
                    requireActivity(),
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

    private fun updateHealthAdapter(data: List<Content>) {
        homeBinding.apply {
            if (data.isEmpty()) healthTitleContainer.visibility = View.GONE
            else healthTitleContainer.visibility = View.VISIBLE
            healthRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = HealthListAdapter(data)
            healthRecyclerView.adapter = adapter
        }
    }

    private fun updateLifeAdapter(data: List<Content>) {
        homeBinding.apply {
            if (data.isEmpty()) homeBinding.lifeTitleContainer.visibility = View.GONE
            else homeBinding.lifeTitleContainer.visibility = View.VISIBLE
            lifeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = LifeListAdapter(data)
            lifeRecyclerView.adapter = adapter
        }
    }

    private fun updateWorkStudyAdapter(data: List<Content>) {
        homeBinding.apply {
            if (data.isEmpty()) homeBinding.workStudyTitleContainer.visibility = View.GONE
            else homeBinding.workStudyTitleContainer.visibility = View.VISIBLE
            workStudyRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = WorkStudyListAdapter(data)
            workStudyRecyclerView.adapter = adapter
        }
    }
}