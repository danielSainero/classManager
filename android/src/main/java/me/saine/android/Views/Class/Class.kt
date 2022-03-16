package me.saine.android.Views.Class

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MainClass(
    navController: NavController,
    mainViewModelClass: MainViewModelClass
){
    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Mi Clase")
                 }
             )
        },
        content = {

        }
    )
}