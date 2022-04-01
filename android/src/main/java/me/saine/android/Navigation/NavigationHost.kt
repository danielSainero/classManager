package com.example.classmanegerandroid.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.classmanegerandroid.Views.Login.MainLogin
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import me.saine.android.Views.Register.MainRegister
import me.saine.android.Views.Class.MainClass
import me.saine.android.Views.Class.MainViewModelClass
import me.saine.android.Views.Course.MainCourse
import me.saine.android.Views.Course.MainViewModelCourse
import me.saine.android.Views.CreateClass.MainCreateClass
import me.saine.android.Views.CreateClass.MainViewModelCreateClass
import me.saine.android.Views.CreateCourse.MainCreateCourse
import me.saine.android.Views.CreateCourse.MainViewModelCreateCourse
import me.saine.android.Views.Login.ForgotPassword.MainForgotPassword
import me.saine.android.Views.Login.ForgotPassword.MainViewModelForgotPassword
import me.saine.android.Views.MainAppActivity.MainAppView
import me.saine.android.Views.MainAppActivity.MainViewModelMainAppView
import me.saine.android.Views.Practice.MainPractice
import me.saine.android.Views.Practice.MainViewModelPractice
import me.saine.android.Views.Register.MainRegister
import me.saine.android.Views.Register.MainViewModelRegister
import me.saine.android.Views.Register.PrivacyPolicies.MainPrivacyPolicies
import me.saine.android.Views.Register.PrivacyPolicies.MainViewModelPrivacyPolicies
import me.saine.android.Views.Settings.MainSettings
import me.saine.android.Views.Settings.MyAccount.MainMyAccount
import me.saine.android.Views.Settings.MyAccountOptions.MainMyAccountOptions

lateinit var navController: NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(
    startDestination: String,
    mainViewModelLogin: MainViewModelLogin,
    mainViewModelRegister: MainViewModelRegister,
    mainViewModelMainAppView: MainViewModelMainAppView,
    mainViewModelCreateCourse: MainViewModelCreateCourse,
    mainViewModelCreateClass: MainViewModelCreateClass,
    mainViewModelCourse: MainViewModelCourse,
    mainViewModelClass: MainViewModelClass,
    mainViewModelPrivacyPolicies: MainViewModelPrivacyPolicies,
    mainViewModelForgotPassword: MainViewModelForgotPassword,
    mainViewModelPractice: MainViewModelPractice
) {


    navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
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
        composable(route = Destinations.Settings.route) {
            MainSettings(
                navController = navController,
            )
        }
        composable(route = Destinations.MyAccount.route) {
            MainMyAccount(
                navController = navController,
            )
        }
        composable(route = Destinations.MyAccountOptions.route) {
            MainMyAccountOptions(
                navController = navController,
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

        composable(route = Destinations.ForgotPassword.route) {
            MainForgotPassword(
                navController = navController,
                mainViewModelForgotPassword = mainViewModelForgotPassword
            )
        }
        composable(
            route = "${Destinations.Practice.route}/{idPractice}",
            arguments = listOf(navArgument("idPractice"){type = NavType.StringType})
        ) {
            val idPractice = it.arguments?.getString("idPractice")
            requireNotNull(idPractice)
            MainPractice(
                navController = navController,
                mainViewModelPractice = mainViewModelPractice,
                idPractice = idPractice
            )
        }

        composable(
            route = "${Destinations.Course.route}/{courseId}",
            arguments = listOf(navArgument("courseId"){type = NavType.StringType})
        ) {
            val courseId = it.arguments?.getString("courseId")
            requireNotNull(courseId)
            MainCourse(
                navController = navController,
                mainViewModelCourse = mainViewModelCourse,
                courseId = courseId
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
                    mainViewModelClass = mainViewModelClass,
                    classId = classId
                )
            }
        )
        composable(
            route = Destinations.PrivacyPolicies.route,
            content = {
                MainPrivacyPolicies(
                    navController= navController,
                    mainViewModelPrivacyPolicies = mainViewModelPrivacyPolicies
                )
            }
        )
    }

}