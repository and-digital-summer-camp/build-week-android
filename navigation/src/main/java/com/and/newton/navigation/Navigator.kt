package com.and.newton.navigation

import androidx.navigation.NavController
import javax.inject.Inject

class Navigator @Inject constructor(){

    fun loginNavigateToComms(navController: NavController){
        navController.navigate(R.id.action_login_to_nav_comms)
    }



}