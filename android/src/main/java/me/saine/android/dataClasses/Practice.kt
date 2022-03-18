package me.saine.android.dataClasses

data class Practice(
    val id: String,
    val description: String,
    val idOfChats: MutableList<String>,
    val teacherAnnotation: String,
    val name: String
)
