package com.oze.footballfixtures.ui.compete.teamFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.oze.footballfixtures.core.BaseFragment
import com.oze.footballfixtures.databinding.FragmentTeamBinding
import com.oze.footballfixtures.presentation.NetworkStatus
import com.oze.footballfixtures.presentation.TeamResponse
import com.oze.footballfixtures.ui.compete.CompetitionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamFragment : BaseFragment() {

    private val viewModel  by viewModels<CompetitionDetailsViewModel>()
    private lateinit var adapter: TeamAdapter
    private var _binding: FragmentTeamBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTeams(competitionId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.click = ClickHandler()
        getIntents()
        initRecyclerView()
        return root
    }

    private fun getIntents() {
        competitionId = arguments?.getLong("id")!!
    }

    private fun initRecyclerView() {
        adapter = TeamAdapter(ArrayList())
        binding.teamRecyclerview.adapter = adapter
        binding.teamRecyclerview.layoutManager = GridLayoutManager(context, 3)
    }

    private fun getTeams(id: Long) {
        viewModel.getTeams(id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkStatus.Loading -> {
                    showLoading()
                }
                is NetworkStatus.Error -> {
                    hideLoading()
                    getTeamsFailed(result.errorMessage!!)
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    result.data?.let { getTeamsSuccessful(it) }
                }
            }
        })
    }

    private fun getTeamsSuccessful(teamResponse: TeamResponse) {
        if (teamResponse.teams.isNullOrEmpty()) {
            binding.noData.visibility = View.VISIBLE
        } else {
            binding.teamRecyclerview.visibility = View.VISIBLE
            teamResponse.let { adapter.updateAdapter(it.teams) }
        }
    }

    private fun getTeamsFailed(message: String) {
        show(message, true)
        showRetryMessage()
    }

    private fun showRetryMessage() {
        binding.teamRecyclerview.visibility = View.GONE
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
            getTeams(competitionId)
        }
    }

    companion object {
        fun newInstance() = TeamFragment()
        var competitionId: Long = 0L
    }
}