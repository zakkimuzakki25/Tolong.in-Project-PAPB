package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import com.papb.tolonginprojectpapb.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _avatarUrl = MutableLiveData<String>()
    val avatarUrl: LiveData<String> = _avatarUrl

    private val _xp = MutableLiveData<Int>()
    val xp: LiveData<Int> = _xp

    private val _carbonData = MutableLiveData<Pair<Double, Double>>()
    val carbonData: LiveData<Pair<Double, Double>> = _carbonData

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> = _birthDate

    init {
        fetchUserData()
        fetchCarbonData()
        loadProfilePosts()
    }

    private fun fetchUserData() {
        userId?.let { uid ->
            firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _name.value = document.getString("fullname") ?: ""
                        _phoneNumber.value = document.getString("phone_number") ?: ""
                        _birthDate.value = document.getString("birth_date") ?: ""
                        _avatarUrl.value = document.getString("avatar_url") ?: ""
                    }
                }
                .addOnFailureListener { e ->
                    println("Failed to fetch user data: ${e.message}")
                }
        }
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun updateBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    fun saveProfile(name: String, phoneNumber: String, birthDate: String) {
        userId?.let { uid ->
            val updatedData = mapOf(
                "fullname" to name,
                "phone_number" to phoneNumber,
                "birth_date" to birthDate
            )

            firestore.collection("users")
                .document(uid)
                .update(updatedData)
                .addOnSuccessListener {
                    println("Profile updated successfully")
                }
                .addOnFailureListener { e ->
                    println("Failed to update profile: ${e.message}")
                }
        }
    }

    private fun fetchCarbonData() {
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val co2Saved = document.getDouble("co2_saved") ?: 0.0
                        val waterSaved = document.getDouble("water_saved") ?: 0.0
                        _carbonData.postValue(Pair(co2Saved, waterSaved))
                    } else {
                        _carbonData.postValue(Pair(0.0, 0.0))
                    }
                }
                .addOnFailureListener { e ->
                    println("Error fetching CO2 data: ${e.message}")
                    _carbonData.postValue(Pair(0.0, 0.0))
                }
        } ?: run {
            _carbonData.postValue(Pair(0.0, 0.0))
        }
    }

    fun loadProfilePosts() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val forums = firestore.collection("forums")
                    .whereEqualTo("user_id", userId)
                    .get()
                    .await()
                    .toObjects(Forum::class.java)
                    .mapNotNull { forum ->
                        forum.copy(id = forum.id)
                    }

                val missionsSnapshot = firestore.collection("campaign_missions")
                    .get()
                    .await()

                val missions = missionsSnapshot.documents.associate { document ->
                    val mission = document.toObject(CampaignMission::class.java)
                    document.id to mission?.copy(id = document.id)
                }

                val combinedPosts = forums.mapNotNull { forum ->
                    val mission = missions[forum.mission_id]
                    if (mission != null) {
                        Post(forum, mission)
                    } else {
                        null
                    }
                }

                _posts.value = combinedPosts
            } catch (e: Exception) {
                println("Error loading profile posts: ${e.message}")
            }
        }
    }


    fun updateProfile(newName: String, newPhoneNumber: String, newEmail: String) {
        val userId = auth.currentUser?.uid ?: return
        val updatedData = mapOf(
            "fullname" to newName,
            "phone_number" to newPhoneNumber,
            "email" to newEmail
        )

        firestore.collection("users")
            .document(userId)
            .update(updatedData)
            .addOnSuccessListener {
                _name.value = newName
                _phoneNumber.value = newPhoneNumber
                _email.value = newEmail
            }
            .addOnFailureListener { e ->
                println("Failed to update profile: ${e.message}")
            }
    }
}
