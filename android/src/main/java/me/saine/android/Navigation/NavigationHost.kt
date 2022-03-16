package com.example.classmanegerandroid.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.classmanegerandroid.Views.Login.MainLogin
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import com.example.classmanegerandroid.Views.MainRegister
import me.saine.android.Views.Class.MainClass
import me.saine.android.Views.Class.MainViewModelClass
import me.saine.android.Views.Course.MainCourse
import me.saine.android.Views.Course.MainViewModelCourse
import me.saine.android.Views.CreateClass.MainCreateClass
import me.saine.android.Views.CreateClass.MainViewModelCreateClass
import me.saine.android.Views.CreateCourse.MainCreateCourse
import me.saine.android.Views.CreateCourse.MainViewModelCreateCourse
import me.saine.android.Views.MainAppActivity.MainAppView
import me.saine.android.Views.MainAppActivity.MainViewModelMainAppView
import me.saine.android.Views.Register.MainViewModelRegister
import me.saine.android.Views.Register.PrivacyPolicies.MainPrivacyPolicies

@Composable
fun NavigationHost(
    mainViewModelLogin: MainViewModelLogin,
    mainViewModelRegister: MainViewModelRegister,
    mainViewModelMainAppView: MainViewModelMainAppView,
    mainViewModelCreateCourse: MainViewModelCreateCourse,
    mainViewModelCreateClass: MainViewModelCreateClass,
    mainViewModelCourse: MainViewModelCourse,
    mainViewModelClass: MainViewModelClass
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
            MainAppView(
                navController = navController,
                mainViewModelMainAppView = mainViewModelMainAppView
            )
        }
        composable(route = Destinations.CreateCourse.route) {
            mainViewModelCreateCourse.createListItems()
            MainCreateCourse(
                navController = navController,
                mainViewModelCreateCourse = mainViewModelCreateCourse
            )
        }
        composable(route = Destinations.CreateClass.route) {
            MainCreateClass(
                navController = navController,
                mainViewModelCreateClass = mainViewModelCreateClass
            )
        }
        composable(
            route = "${Destinations.Course.route}/{courseId}",
            arguments = listOf(navArgument("courseId"){type = NavType.StringType})
        ) {
            val courseId = it.arguments?.getString("courseId")
            requireNotNull(courseId)
            mainViewModelCourse.getSelectedCourse(courseId)
            MainCourse(
                navController = navController,
                mainViewModelCourse = mainViewModelCourse
            )
        }
        composable(
            route = "${Destinations.Class.route}/{classId}",
            arguments = listOf(navArgument("classId"){type = NavType.StringType}),
            content = {
                val classId = it.arguments?.getString("classId")
                requireNotNull(classId)

                MainClass(
                    navController = navController,
                    mainViewModelClass = mainViewModelClass
                )
            }
        )
        composable(
            route = Destinations.PrivacyPolicies.route,
            content = {
                MainPrivacyPolicies(
                    navController= navController
                )
            }
        )
    }

}