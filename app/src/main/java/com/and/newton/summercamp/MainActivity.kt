package com.and.newton.summercamp


import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.and.newton.common.viewmodel.UserViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)

        window.statusBarColor = getColor(R.color.color_primary_shrine_pink)

        setContentView(R.layout.activity_main)

        this.drawerLayout = findViewById(R.id.drawer_layout)
        this.toolbar = findViewById(R.id.toolbar)
        this.fab = findViewById(R.id.fab)

        initNavigationDrawerWithToolBar()
        initCreateArticleButton()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.login -> {
                    toolbar?.visibility = View.GONE
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    fab?.hide()
                }
                else -> {
                    toolbar?.visibility = View.VISIBLE
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    fab?.show()
                }
            }

        }}

    private fun initCreateArticleButton() {
        val floatingActionButton: FloatingActionButton = findViewById(R.id.fab)
        floatingActionButton.setOnClickListener {
            val uri = Uri.parse("App://nav_create_article")
            navController.navigate(uri)
        }
    }


    private fun initNavigationDrawerWithToolBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navView: NavigationView = findViewById(R.id.nav_view)
        this.navController = findNavController(R.id.nav_host_fragment)

        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            userViewModel.signout()
            navController.popBackStack(R.id.login, false)
            return@setOnMenuItemClickListener true
        }

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_comms ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}