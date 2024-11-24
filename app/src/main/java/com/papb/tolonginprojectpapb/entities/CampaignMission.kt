package com.papb.tolonginprojectpapb.entities

import com.google.firebase.firestore.PropertyName

data class CampaignMission(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val description: String = "",
    @get:PropertyName("is_big") @set:PropertyName("is_big")
    var is_big: Boolean = false,
    val benefits: List<String> = emptyList(),
    val plus_xp: Int = 0,
    val category: String = "",
    val illustration_url: String = "",
    val water_saved: Float = 0f,
    val co2_saved: Float = 0f,
    val electricity_saved: Float = 0f
)
