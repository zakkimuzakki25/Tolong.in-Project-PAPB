package com.papb.tolonginprojectpapb.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.OpenVolunteer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VolunteerDetailViewModel(private val volunteerId: String) : ViewModel() {
    private val _volunteerDetail = MutableStateFlow<OpenVolunteer?>(null)
    val volunteerDetail: StateFlow<OpenVolunteer?> = _volunteerDetail

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchVolunteerDetail()
    }

    fun addXPUser(userId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userRef = firestore.collection("users").document(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val currentXp = document.getLong("xp") ?: 0
                    val newXp = currentXp + 50

                    userRef.update("xp", newXp)
                        .addOnSuccessListener {
                            println("XP berhasil diperbarui menjadi $newXp")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            println("Gagal memperbarui XP: ${e.message}")
                            onFailure(e.message ?: "Gagal memperbarui XP")
                        }
                } else {
                    onFailure("Data pengguna tidak ditemukan")
                }
            }
            .addOnFailureListener { e ->
                println("Gagal mengambil data pengguna: ${e.message}")
                onFailure(e.message ?: "Gagal mengambil data pengguna")
            }
    }

    fun submitActivity(
        userId: String,
        volunteerId: String,
        kegiatan: String,
        dokumentasi: Uri?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val data = hashMapOf(
            "userId" to userId,
            "volunteerId" to volunteerId,
            "kegiatan" to kegiatan,
            "dokumentasi" to (dokumentasi?.toString() ?: "")
        )
        FirebaseFirestore.getInstance().collection("activities")
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Unknown error") }
    }


    fun checkUserJoined(userId: String, volunteerId: String, onResult: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users_volunteers")
            .whereEqualTo("user_id", userId)
            .whereEqualTo("volunteer_id", volunteerId)
            .get()
            .addOnSuccessListener { documents ->
                onResult(!documents.isEmpty)
            }
            .addOnFailureListener { e ->
                println("Gagal memeriksa relasi: ${e.message}")
                onResult(false)
            }
    }

    fun joinVolunteer(userId: String, volunteerId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userVolunteerRef = firestore.collection("users_volunteers")

        userVolunteerRef
            .whereEqualTo("user_id", userId)
            .whereEqualTo("volunteer_id", volunteerId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    val newRelasi = hashMapOf(
                        "user_id" to userId,
                        "volunteer_id" to volunteerId,
                        "joined_at" to FieldValue.serverTimestamp()
                    )
                    userVolunteerRef.add(newRelasi)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onFailure(e.message ?: "Gagal bergabung")
                        }
                } else {
                    onFailure("User sudah bergabung dengan volunteer ini.")
                }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Gagal memeriksa relasi")
            }
    }

    private fun fetchVolunteerDetail() {
        viewModelScope.launch {
            firestore.collection("open_volunteers")
                .document(volunteerId)
                .get()
                .addOnSuccessListener { document ->
                    val detail = document.toObject(OpenVolunteer::class.java)?.copy(
                        id = document.id,
                    )
                    _volunteerDetail.value = detail
                }
                .addOnFailureListener {
                    _volunteerDetail.value = null
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val volunteerId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VolunteerDetailViewModel::class.java)) {
                return VolunteerDetailViewModel(volunteerId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
