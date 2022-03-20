package me.saine.android.dataClasses

data class Class(
    val id: String,
    val name: String,
    val description: String,
    val idPractices: MutableList<String>,
    val admins: MutableList<String>,
    val idOfCourse: String
)
