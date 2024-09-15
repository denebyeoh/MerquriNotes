package com.example.merqurinotes.main.summary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merqurinotes.R
import com.example.merqurinotes.base.model.SummarizeData
import com.example.merqurinotes.databinding.FragmentSummaryBinding
import com.example.merqurinotes.main.adapter.SummaryListAdapter
import com.example.merqurinotes.main.viewmodel.MainViewModel
import com.example.merqurinotes.notes.activity.AddNotesActivity
import com.example.merqurinotes.room.Category
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.utils.api.ApiResource

class SummaryFragment : Fragment() {
    private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private lateinit var summaryBinding : FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        summaryBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        return summaryBinding.root
        //return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSummarizeContentFromDB()
    }

    private fun initViewModel() {
        val activity = this@SummaryFragment
        viewModel.apply {
            retrieveCategoryListResponse.observe(
                requireActivity(),
                activity::onRetrieveCategoryFromDBResponse
            )
            retrieveSummarizeCategoryListResponse.observe(
                requireActivity(),
                activity::onRetrieveSummarizeCategoryFromDBResponse
            )
        }
    }

    private fun onRetrieveSummarizeCategoryFromDBResponse(response: ApiResource<List<SummarizeData>>?) {
        when (response) {
            is ApiResource.Loading -> {

            }

            is ApiResource.Success -> {
                updateSummaryAdapter(response.data)
            }

            is ApiResource.Error -> {

            }

            else -> {}
        }
    }

    private fun onRetrieveCategoryFromDBResponse(response: ApiResource<List<Content>>?) {
        when (response) {
            is ApiResource.Loading -> {

            }

            is ApiResource.Success -> {
            }

            is ApiResource.Error -> {

            }

            else -> {}
        }
    }

    private fun updateSummaryAdapter(data: List<SummarizeData>) {
        summaryBinding.apply {
            categoryRecycleView.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = SummaryListAdapter(data)
            categoryRecycleView.adapter = adapter
        }
    }

    private fun initView() {
        summaryBinding.apply {
            customBottomBar.summaryLabel.setTextColor(ContextCompat.getColor(requireActivity(), R.color.pink))
            customBottomBar.summaryButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.summary_undim_icon))
            customBottomBar.homeButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.home_dim_icon))
            customBottomBar.homeContainer.setOnClickListener {
                Navigation.findNavController(summaryBinding.root).navigate(R.id.action_summaryFragment_to_homeFragment)
            }
            customBottomBar.addNotesContainer.setOnClickListener {
                AddNotesActivity.start(requireActivity())
            }
        }
    }
}