package com.oze.footballfixtures.ui.compete.fixtureFragment

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
import com.oze.footballfixtures.databinding.FragmentTeamBinding
import com.oze.footballfixtures.presentation.MatchResponse
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.compete.CompetitionDetailsViewModel
import com.oze.footballfixtures.ui.compete.teamFragment.TeamAdapter
import com.oze.footballfixtures.utils.Utilities.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FixturesFragment : BaseFragment() {

    private val viewModel  by viewModels<CompetitionDetailsViewModel>()
    private lateinit var adapter: FixturesAdapter
    private var _binding: FragmentFixturesBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.click = ClickHandler()
        getIntents()
        initRecyclerView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSingleMatch(competitionId, getCurrentDate())
    }

    private fun getIntents() {
        competitionId = arguments?.getLong("id")!!
    }

    private fun initRecyclerView() {
        adapter = FixturesAdapter(ArrayList())
        binding.fixturesRecyclerview.adapter = adapter
        binding.fixturesRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.fixturesRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun getSingleMatch(id: Long, date: String) {
        viewModel.getSingleMatch(id, date).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkStatus.Loading -> {
                    showLoading()
                }
                is NetworkStatus.Error -> {
                    hideLoading()
                    getSingleMatchFailed(result.errorMessage!!)
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    result.data?.let { getSingleMatchSuccessful(it) }
                }
            }
        })
    }

    private fun getSingleMatchSuccessful(matchResponse: MatchResponse) {
        if (matchResponse.matches.isNullOrEmpty()) {
            binding.fixturesRecyclerview.visibility = View.GONE
            binding.noFixture.visibility = View.VISIBLE
        } else {
            binding.fixturesRecyclerview.visibility = View.VISIBLE
            adapter.updateAdapter(matchResponse.matches)
        }
    }

    private fun getSingleMatchFailed(message: String) {
        show(message, true)
        showRetryMessage()
    }

    private fun showRetryMessage() {
        binding.fixturesRecyclerview.visibility = View.GONE
        binding.noInternet.visibility = View.VISIBLE
    }

    override fun showLoading() {
        binding.includeProgressBar.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.includeProgressBar.progressBar.visibility = View.GONE
    }

    inner class ClickHandler {
        fun onTapToRetry(view: View) {
            binding.noInternet.visibility = View.GONE
            getSingleMatch(competitionId, getCurrentDate())
        }
    }

    companion object {
        fun newInstance() = FixturesFragment()
        var competitionId: Long = 0L
    }
}