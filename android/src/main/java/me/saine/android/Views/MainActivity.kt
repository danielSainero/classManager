package me.saine.android.Views

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import me.saine.common.App
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.NavigationHost
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
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

/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,) {
        super.onActivityResult(requestCode, resultCode, data,)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(
                    idToken = account.idToken!!,
                    navController = navController,
                    context = context
                )
                Toast.makeText(context,"Ha ido2", Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(context,"No Ha ido2", Toast.LENGTH_SHORT).show()

                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            classManagerTheme {
               // mainViewModelMainAppView.getCourses()
                NavigationHost(
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
    }
}