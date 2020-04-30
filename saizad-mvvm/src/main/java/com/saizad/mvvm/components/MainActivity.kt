package com.saizad.mvvm.components

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.saizad.mvvm.R
import kotlinx.android.synthetic.main.activity_home.*


abstract class MainActivity<VM: SaizadBaseViewModel> : SaizadBaseActivity<VM>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainFragmentHost) as NavHostFragment? ?: return

        val navController = host.navController
        val topLevelDestinationIds = setOf(R.id.mainFragmentHost)
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds)

        setupActionBar(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

        }
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.mainFragmentHost))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainFragmentHost).navigateUp(appBarConfiguration)
    }

}
