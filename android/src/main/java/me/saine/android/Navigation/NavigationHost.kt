package com.example.classmanegerandroid.Navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.classmanegerandroid.Views.Login.MainLogin
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import com.example.classmanegerandroid.Views.MainRegister
import me.saine.android.Views.MainAppActivity.MainAppView
import me.saine.android.Views.MainAppActivity.MainViewModelMainAppView
import me.saine.android.Views.Register.MainViewModelRegister

@Composable
fun NavigationHost(
    mainViewModelLogin: MainViewModelLogin,
    mainViewModelRegister: MainViewModelRegister,
    mainViewModelMainAppView: MainViewModelMainAppView
) {

    var navController: NavHostController
    navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login.route
    ) {

        composable(route = Destinations.Login.route) {
           MainLogin(
               navController = navController,
               mainViewModelLogin = mainViewModelLogin
           )
        }
        composable(route = Destinations.Register.route) {
           MainRegister(
               navController = navController,
               mainViewModelRegister = mainViewModelRegister
           )
        }
        composable(route = Destinations.MainAppView.route) {
            //mainViewModelMainAppView.getCourses()
            //mainViewModelMainAppView.myUser()
            MainAppView(
                navController = navController,
                mainViewModelMainAppView = mainViewModelMainAppView
            )
        }

    }

}