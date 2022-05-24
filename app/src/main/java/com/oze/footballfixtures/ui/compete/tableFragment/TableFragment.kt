package com.oze.footballfixtures.ui.compete.tableFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.R
import com.oze.footballfixtures.core.BaseFragment
import com.oze.footballfixtures.databinding.FragmentFixturesBinding
import com.oze.footballfixtures.databinding.FragmentTableBinding
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.StandingResponse
import com.oze.footballfixtures.ui.compete.CompetitionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableFragment : BaseFragment() {


    private lateinit var adapter: TableAdapter
    private val viewModel  by viewModels<CompetitionDetailsViewModel>()

    lateinit var _binding: FragmentTableBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.click = ClickHandler()
        getIntents()
        initRecyclerView()
       return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStandings(competitionId)
    }
    private fun getIntents() {
        competitionId = arguments?.getLong("id")!!
    }

    private fun initRecyclerView() {
        adapter = TableAdapter(ArrayList())
        binding.tableRecyclerview.adapter = adapter
        binding.tableRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.tableRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun getStandings(id: Long) {
        viewModel.getStandings(id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkStatus.Loading -> {
                    showLoading()
                }
                is NetworkStatus.Error -> {
                    hideLoading()
                    getStandingsFailed(result.errorMessage!!)
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    result.data?.let { getStandingsSuccessful(it) }
                }
            }
        })
    }

    private fun getStandingsSuccessful(standingResponse: StandingResponse) {
        if (standingResponse.standings.isNullOrEmpty()) {
            binding.noData.visibility = View.VISIBLE
        } else {
            binding.tableRecyclerview.visibility = View.VISIBLE
            standingResponse.standings[0].table.let { adapter.updateAdapter(it) }
        }
    }

    private fun getStandingsFailed(message: String) {
        show(message, true)
        showRetryMessage()
    }

    private fun showRetryMessage() {
        binding.tableRecyclerview.visibility = View.GONE
        binding.noInternet.visibility = View.VISIBLE
    }

    override fun showLoading() {
        binding.includeProgressBar.progressBar .visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.includeProgressBar.progressBar .visibility = View.GONE
    }

    inner class ClickHandler {
        fun onTapToRetry(view: View) {
            binding.noInternet.visibility = View.GONE
            getStandings(competitionId)
        }
    }

    companion object {
        fun newInstance() = TableFragment()
        var competitionId: Long = 0L
    }

}