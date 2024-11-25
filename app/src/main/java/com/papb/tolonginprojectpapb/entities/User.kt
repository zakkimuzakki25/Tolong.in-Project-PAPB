package com.papb.tolonginprojectpapb.entities

data class User(
    val username: String = "",
    val email: String = "",
    val fullname: String = "",
    val password: String = "",
    val avatar_url: String = "",
    val birth_date: String = "",
    val xp: Int = 0,
    val phone_number: String = "",
    val ktp_url: String = "",
    val status: String = "",
    val joined_volunteers: MutableList<String> = mutableListOf()
)
