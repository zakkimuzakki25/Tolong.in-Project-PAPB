package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.OpenVolunteer

class AksiViewModel : ViewModel() {

    private val _volunteerList = MutableLiveData<List<OpenVolunteer>>()
    val volunteerList: LiveData<List<OpenVolunteer>> = _volunteerList

    private val _dailyMissionList = MutableLiveData<List<CampaignMission>>()
    val dailyMissionList: LiveData<List<CampaignMission>> = _dailyMissionList

    private val _bigMissionList = MutableLiveData<List<CampaignMission>>()
    val bigMissionList: LiveData<List<CampaignMission>> = _bigMissionList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchVolunteers()
        fetchDailyMissions()
        fetchBigMissions()
    }

    private fun fetchDailyMissions() {
        val db = FirebaseFirestore.getInstance()
        db.collection("campaign_missions").whereEqualTo("is_big", false).get()
            .addOnSuccessListener { result ->
                val missions = result.mapNotNull { document ->
                    document.toObject(CampaignMission::class.java)
                }
                _dailyMissionList.value = missions
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to fetch daily missions: ${exception.localizedMessage}"
            }
    }

    private fun fetchBigMissions() {
        val db = FirebaseFirestore.getInstance()
        db.collection("campaign_missions").whereEqualTo("is_big", true).get()
            .addOnSuccessListener { result ->
                val missions = result.mapNotNull { document ->
                    document.toObject(CampaignMission::class.java)
                }
                _bigMissionList.value = missions
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to fetch big missions: ${exception.localizedMessage}"
            }
    }

    private fun fetchVolunteers() {
        _isLoading.value = true
        val db = FirebaseFirestore.getInstance()

        db.collection("open_volunteers").get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    _volunteerList.value = emptyList()
                    _errorMessage.value = "No volunteers found."
                } else {
                    val volunteers = result.mapNotNull { document ->
                        document.toObject(OpenVolunteer::class.java).copy(
                            id = document.id
                        )
                    }
                    _volunteerList.value = volunteers
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to fetch data: ${exception.localizedMessage}"
                _isLoading.value = false
            }
    }

}
