package me.saine.android.dataClasses

data class Chat(
    val id: String,
    val conversation: MutableList<Message>
)
