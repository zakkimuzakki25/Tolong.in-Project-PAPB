package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel : ViewModel() {

    private val _name = MutableLiveData("Malin Kundang")
    val name: LiveData<String> = _name

    private val _phoneNumber = MutableLiveData("081267868889")
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _email = MutableLiveData("timun19@gmail.com")
    val email: LiveData<String> = _email

    fun updateProfile(newName: String, newPhoneNumber: String, newEmail: String) {
        _name.value = newName
        _phoneNumber.value = newPhoneNumber
        _email.value = newEmail
    }
}
