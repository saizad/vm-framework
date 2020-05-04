package com.saizad.mvvmexample.components.main

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.saizad.mvvmexample.R
import com.saizad.mvvmexample.components.MVVMExampleActivity
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_home.*


class DrawerMainActivity : MVVMExampleActivity<MVVMExampleMainActivityViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainFragmentHost) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        val topLevelDestinationIds = setOf(R.id.homeFragment)
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds, drawer_layout)

        setupActionBar(navController, appBarConfiguration)

        setupNavigationMenu(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->

        }
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val retValue = (item.onNavDestinationSelected(navController())
                || super.onOptionsItemSelected(item))
        return retValue
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController().navigateUp(appBarConfiguration)
    }

    override fun navController(): NavController {
        return findNavController(R.id.mainFragmentHost)
    }

    override fun getViewModelClassType(): Class<MVVMExampleMainActivityViewModel> {
        return MVVMExampleMainActivityViewModel::class.java
    }
}
