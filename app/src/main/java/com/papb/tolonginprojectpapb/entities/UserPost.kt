package com.papb.tolonginprojectpapb.entities

data class UserPost(
    val username: String,
    val avatarUrl: String,
    val timestamp: String,
    val content: String,
    val xp: Int,
    val missionName: String,
    val missionCategory: String
)
