package com.papb.tolonginprojectpapb.entities

data class User(
    val username: String,
    val email: String,
    val password: String,
    val photo_url: String,
    val xp: Int,
    val phone_number: String,
    val ktp_url: String,
    val status: String,
    val joined_volunteers: MutableList<String> = mutableListOf() // Relasi dengan OpenVolunteer
)
