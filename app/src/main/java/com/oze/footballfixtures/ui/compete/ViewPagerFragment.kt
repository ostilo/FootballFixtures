package com.oze.footballfixtures.ui.compete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.oze.footballfixtures.R
import com.oze.footballfixtures.core.BaseFragment
import com.oze.footballfixtures.databinding.FragmentDashboardBinding
import com.oze.footballfixtures.databinding.FragmentViewPagerBinding
import com.oze.footballfixtures.ui.compete.adapter.ViewPagerAdapter
import com.oze.footballfixtures.ui.compete.fixtureFragment.FixturesFragment
import com.oze.footballfixtures.ui.compete.tableFragment.TableFragment
import com.oze.footballfixtures.ui.compete.teamFragment.TeamFragment
import com.oze.footballfixtures.ui.competition.TodayFixturesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewPagerFragment : BaseFragment() {


    private var _binding: FragmentViewPagerBinding? = null
    private var competitionId: Long = 0L
    private lateinit var name: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun showLoading() {

    }

    override fun hideLoading() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getIntents()
        initAdapter()

        // Set the actionbar title
        baseActivity.supportActionBar?.title = name
        return  root
    }

    private fun initAdapter(){
        val viewPageAdapter = ViewPagerAdapter(
            childFragmentManager
        )
        viewPageAdapter.addFragment(TableFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Table")
        viewPageAdapter.addFragment(FixturesFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Fixtures")
        viewPageAdapter.addFragment(TeamFragment.newInstance().apply {
            arguments = Bundle().apply {
                putLong("id", competitionId)
            }
        }, "Team")

        // Set up the ViewPager with the sections adapter.
        binding.container.adapter = viewPageAdapter
        binding.tabs.setupWithViewPager(binding.container)
    }

    private fun getIntents() {
        val args: ViewPagerFragmentArgs by navArgs()
         competitionId = args.competition.id
         name = args.competition.name
    }

}