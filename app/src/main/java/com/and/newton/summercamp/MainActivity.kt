package com.and.newton.summercamp


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.and.newton.common.utils.AppConstants
import com.and.newton.common.utils.AppPreferences
import com.and.newton.common.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
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
        initAfterLogin()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.login -> {
                    toolbar?.visibility = View.GONE
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    fab?.hide()
                }
                R.id.createArticleFragment -> {
                    fab?.hide()
                }
                R.id.viewArticleFragment -> {
                    fab?.hide()
                }
                else -> {
                    toolbar?.visibility = View.VISIBLE
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    if(AppPreferences.access_level == AppConstants.ROLE_ADMIN.toString())  fab?.show() else fab?.hide()
                    initSideMenuWithUserData()
                }
            }
        }
    }


    private fun initCreateArticleButton() {
        val floatingActionButton: FloatingActionButton = findViewById(R.id.fab)
        floatingActionButton.setOnClickListener {
            navController.navigate(com.and.newton.comms.R.id.action_commsLandingPageFragment_to_createArticleFragment)
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
            com.and.newton.comms.R.id.commsLandingPageFragment, R.id.login ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initAfterLogin() {
        userViewModel.authenticatedState.observe(this, Observer { authenticatedState ->
            when (authenticatedState) {
                UserViewModel.AuthenticationState.AUTHENTICATED -> {
                    initSideMenuWithUserData()
                }
            }
        })
    }

    private fun initSideMenuWithUserData() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHeader: View = navView.getHeaderView(0)
        (navHeader.findViewById(R.id.user_email) as TextView)?.text = AppPreferences.email
        if(AppPreferences.user_pic.isNotEmpty()){
            Picasso.get()
                .load(AppPreferences.user_pic)
                .resize(72, 72).centerCrop().into((navHeader.findViewById(R.id.user_profile_pic) as ImageView))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}