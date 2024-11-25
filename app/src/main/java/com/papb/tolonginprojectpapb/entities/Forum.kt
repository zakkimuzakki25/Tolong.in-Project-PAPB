package com.papb.tolonginprojectpapb.entities

data class Forum(
    val username: String,
    val avatar_url: String,
    val time: String,
    val caption: String,
    val likes: Int,
    val comments: Int,
    val mission_id: String,
)