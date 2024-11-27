package com.papb.tolonginprojectpapb.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateForumViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUserInputChange(input: String) {
        _userInput.value = input
    }

    fun submitActivity() {
        val input = _userInput.value
        if (input.isEmpty()) return

        _isLoading.value = true
        firestore.collection("forums").add(mapOf("title" to input))
            .addOnCompleteListener { _isLoading.value = false }
    }
}
