package com.example.classmanegerandroid.Views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import com.example.classmanegerandroid.Views.Login.MainLogin


@Composable
fun MainRegister(
    navController: NavController
) {

    Button(
        onClick = {
            navController.navigate(Destinations.Login.route)
        },
        content = {
            Text(text = "Ir al login")
        }
    )
}