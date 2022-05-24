package com.oze.footballfixtures.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oze.footballfixtures.core.BaseFragment
import com.oze.footballfixtures.databinding.FragmentDashboardBinding
import com.oze.footballfixtures.presentation.Competitions
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.ui.competition.TodayFixturesViewModel
import com.oze.footballfixtures.ui.dashboard.adapter.CompetitionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adapter: CompetitionsAdapter

    private val viewModel  by viewModels<TodayFixturesViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.click = ClickHandler()
        initRecyclerView()
        return root
    }
    private fun initRecyclerView() {
        adapter = CompetitionsAdapter(ArrayList(), clickListener)
        binding.competitionsRecyclerview.adapter = adapter
        binding.competitionsRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.competitionsRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCompetitions()
        getCompetitions()
    }

    private fun getCompetitions() {
        viewModel.competitionsLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkStatus.Loading -> {
                    if (result.data.isNullOrEmpty().not()) {
                        hideLoading()
                        result.data?.let { getCompetitionsSuccessful(it) }
                    } else showLoading()
                }
                is NetworkStatus.Error -> {
                    if (result.data.isNullOrEmpty().not()) {
                        hideLoading()
                        result.data?.let { getCompetitionsSuccessful(it) }
                    } else {
                        hideLoading()
                        getCompetitionsFailed(result.errorMessage!!)
                    }
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    result.data?.let { getCompetitionsSuccessful(it) }
                }
            }
        })
    }

    private fun getCompetitionsSuccessful(competitions: List<Competitions>) {
        if (competitions.isNullOrEmpty()) binding.noCompetition.visibility = View.VISIBLE
        else adapter.updateAdapter(competitions)
    }

    private fun getCompetitionsFailed(message: String) {
        show(message, true)
    }

    override fun showLoading() {
        binding.includeProgressBar.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.includeProgressBar.progressBar.visibility = View.GONE
    }

    private val clickListener = View.OnClickListener {
        val competitions = it.tag as Competitions
        val action = DashboardFragmentDirections.actionCompetitionsFragmentToViewPagerNavGraph(
            competitions
        )
        it.findNavController().navigate(action)
    }

    inner class ClickHandler {
        fun onTapToRetry(view: View) {
            binding.noInternet.visibility = View.GONE
            getCompetitions()
        }
    }

}