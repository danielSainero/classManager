package dataClasses

data class appUser(
    val email: String,
    val imgPath: String,
    val name: String,
    val courses: MutableList<String>,
    val classes: MutableList<String>
)
