package me.saine.android.data.local

import me.saine.android.data.remote.appUser

data class Message (
    val message: String,
    val sentBy: appUser,
    val sentOn: String
)
