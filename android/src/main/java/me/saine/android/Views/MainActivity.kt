package me.saine.android.Views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.classmanegerandroid.Navigation.Destinations
import com.example.classmanegerandroid.Navigation.NavigationHost
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import me.saine.android.Views.Class.MainViewModelClass
import me.saine.android.Views.Course.MainViewModelCourse
import me.saine.android.Views.CreateClass.MainViewModelCreateClass
import me.saine.android.Views.CreateCourse.MainViewModelCreateCourse
import me.saine.android.Views.Login.ForgotPassword.MainViewModelForgotPassword
import me.saine.android.Views.MainAppActivity.MainViewModelMainAppView
import me.saine.android.Views.Practice.MainViewModelPractice
import me.saine.android.Views.Register.MainViewModelRegister
import me.saine.android.Views.Register.PrivacyPolicies.MainViewModelPrivacyPolicies
import sainero.dani.intermodular.Views.ui.theme.classManagerTheme
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import me.saine.android.data.network.AccesToDataBase.Companion.auth

//SHA-1: 42:15:5B:23:7D:D6:30:B1:0C:FB:B8:6E:7F:76:2D:8A:92:95:18:40

class MainActivity : AppCompatActivity() {
    private val mainViewModelLogin by viewModels<MainViewModelLogin>()
    private val mainViewModelRegister by viewModels<MainViewModelRegister>()
    private val mainViewModelMainAppView by viewModels<MainViewModelMainAppView>()
    private val mainViewModelCreateCourse by viewModels<MainViewModelCreateCourse>()
    private val mainViewModelCreateClass by viewModels<MainViewModelCreateClass>()
    private val mainViewModelCourse by viewModels<MainViewModelCourse>()
    private val mainViewModelClass by viewModels<MainViewModelClass>()
    private val mainViewModelPrivacyPolicies by viewModels<MainViewModelPrivacyPolicies>()
    private val mainViewModelForgotPassword by viewModels<MainViewModelForgotPassword>()
    private val mainViewModelPractice by viewModels<MainViewModelPractice>()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            mainViewModelLogin.finishLogin(task)

        }
    }




    var startDestination: String = Destinations.Login.route

    @RequiresApi(Build.VERSION_CODES.O)
    @Override
    override fun onStart() {
        super.onStart()

        //auth.signOut()
        val user = auth.currentUser
        if (user != null) {
            startDestination = Destinations.MainAppView.route
            mainViewModelLogin.saveCurrentUser {
                setContent {
                    classManagerTheme {
                        chargeScreen()
                    }
                }
            }
        } else {
            setContent {
                classManagerTheme {
                    chargeScreen()
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun chargeScreen() {
        NavigationHost(
            startDestination = startDestination,
            mainViewModelLogin = mainViewModelLogin,
            mainViewModelRegister = mainViewModelRegister,
            mainViewModelMainAppView = mainViewModelMainAppView,
            mainViewModelCreateCourse = mainViewModelCreateCourse,
            mainViewModelCreateClass = mainViewModelCreateClass,
            mainViewModelCourse = mainViewModelCourse,
            mainViewModelClass = mainViewModelClass,
            mainViewModelPrivacyPolicies = mainViewModelPrivacyPolicies,
            mainViewModelForgotPassword = mainViewModelForgotPassword,
            mainViewModelPractice = mainViewModelPractice
        )
    }
}