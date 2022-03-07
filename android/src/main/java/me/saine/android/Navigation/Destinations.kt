package com.example.classmanegerandroid.Navigation

sealed class Destinations(
    val route: String
) {
    object Login: Destinations(route = "Views.Login.Login")
    object Register: Destinations(route = "Views.Register")
}
