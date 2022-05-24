package com.oze.footballfixtures.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.core.BaseFragment
import com.oze.footballfixtures.databinding.FragmentHomeBinding
import com.oze.footballfixtures.presentation.MatchResponse
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.adapter.TodayFixturesAdapter
import com.oze.footballfixtures.utils.Utilities.getCurrentDate
import com.oze.footballfixtures.utils.Utilities.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFixturesFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TodayFixturesAdapter
    private val homeViewModel  by viewModels<TodayFixturesViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeRecyclerView()
        return root
    }

    private fun initializeRecyclerView() {
        adapter = TodayFixturesAdapter(ArrayList())
        binding.fixturesRecyclerview.adapter = adapter
        binding.fixturesRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.fixturesRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTodayMatch(getCurrentDate())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Fetch list of all fixtures
     */
    private fun getTodayMatch(date: String) {
        if (hasInternetConnection(requireContext()))
            homeViewModel.getAllMatches(date).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is NetworkStatus.Loading -> {
                        showLoading()
                    }
                    is NetworkStatus.Error -> {
                        hideLoading()
                        getTodayMatchFailed(result.errorMessage!!)
                    }
                    is NetworkStatus.Success -> {
                        hideLoading()
                        result.data?.let { getTodayMatchSuccessful(it) }
                    }
                }
            })
        else {
            showNoInternet()
        }
    }

    private fun getTodayMatchSuccessful(matchResponse: MatchResponse) {
        if (matchResponse.matches.isNotEmpty()) {
            adapter.updateAdapter(matchResponse.matches)
        } else {
            binding.noFixture.visibility = View.VISIBLE
        }
    }

    private fun getTodayMatchFailed(message: String) {
        show(message, true)
    }

    private fun showNoInternet() {
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
            getTodayMatch(getCurrentDate())
        }
    }
}