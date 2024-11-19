package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.OpenVolunteer

class AksiViewModel : ViewModel() {

    private val _volunteerList = MutableLiveData<List<OpenVolunteer>>()
    val volunteerList: LiveData<List<OpenVolunteer>> = _volunteerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchVolunteers()
    }

    private fun fetchVolunteers() {
        _isLoading.value = true
        val db = FirebaseFirestore.getInstance()
        db.collection("open_volunteer").get()
            .addOnSuccessListener { result ->
                val volunteers = result.map { document ->
                    document.toObject(OpenVolunteer::class.java)
                }
                _volunteerList.value = volunteers
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }
}
