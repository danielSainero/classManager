package me.saine.android.Views

import me.saine.common.App
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.classmanegerandroid.Navigation.NavigationHost
import com.example.classmanegerandroid.Views.Login.MainViewModelLogin
import sainero.dani.intermodular.Views.ui.theme.classManagerTheme

class MainActivity : AppCompatActivity() {
    private val mainViewModelLogin by viewModels<MainViewModelLogin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            classManagerTheme {
                NavigationHost(
                    mainViewModelLogin
                )
            }
        }
    }
}