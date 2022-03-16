package me.saine.android.dataClasses

data class Course(
    val admins: MutableList<String>,
    val classes: MutableList<String>,
    val name: String,
    val description: String,
    val id: String
  /*  val students: MutableList<String>,
    val fathers: MutableList<String>,*/
)
