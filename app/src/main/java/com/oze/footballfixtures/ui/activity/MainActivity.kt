package com.oze.footballfixtures.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.oze.footballfixtures.R
import com.oze.footballfixtures.core.BaseActivity
import com.oze.footballfixtures.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        initializeView()
    }

    private  fun initializeView(){
        // Finding the Navigation Controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        navController = navHostFragment.navController

        // Setting Navigation Controller with the BottomNavigationView
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)

        //Setting Up ActionBar with Navigation Controller
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.todayFixtureFragment, R.id.competitionsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        // This helps to customise the toolbar i.e the back button
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.viewPagerFragment) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            }
        }
    }

    /**
     * This enables click listener on the back button
     */
    override fun onSupportNavigateUp() =
        findNavController(R.id.fragNavHost).navigateUp(appBarConfiguration)

}