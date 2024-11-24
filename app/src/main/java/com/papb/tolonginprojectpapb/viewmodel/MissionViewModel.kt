package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission

class MissionViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _missionDetail = MutableLiveData<CampaignMission?>()
    val missionDetail: LiveData<CampaignMission?> get() = _missionDetail

    fun fetchMissionById(id: String) {
        println("Fetching mission with ID: $id")
        firestore.collection("campaign_missions")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    println("Mission found with ID: $id")
                    val mission = document.toObject(CampaignMission::class.java)?.copy(id = document.id)
                    _missionDetail.value = mission
                } else {
                    println("Document with ID $id does not exist.")
                    _missionDetail.value = null
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching mission by ID: ${exception.localizedMessage}")
                _missionDetail.value = null
            }
    }
}
