package me.saine.android.data.remote

import me.saine.android.data.local.Message

data class Chat(
    val id: String,
    val conversation: MutableList<Message>
)
