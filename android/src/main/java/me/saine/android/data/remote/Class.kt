package me.saine.android.data.remote

import me.saine.android.data.local.RolUser

data class Class (
    var id: String,
    val name: String,
    val description: String,
    val idPractices: MutableList<String>,
    val users: MutableList<RolUser>,
    val idOfCourse: String
)
