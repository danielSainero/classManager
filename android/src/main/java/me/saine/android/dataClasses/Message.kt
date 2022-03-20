package me.saine.android.dataClasses

data class Message(
    val message: String,
    val sentBy: appUser,
    val sentOn: String
)
