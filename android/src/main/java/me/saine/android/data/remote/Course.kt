package me.saine.android.data.remote

import me.saine.android.data.local.RolUser

data class Course(
    val users: MutableList<RolUser>,
    val classes: MutableList<String>,
    val name: String,
    val description: String,
    var id: String
)
