package com.papb.tolonginprojectpapb.entities

data class Forum(
    val username: String = "",
    val avatar_url: String = "",
    val time: String = "",
    val caption: String = "",
    val likes: Int = 0,
    val comments: Int = 0,
    val mission_id: String = ""
)