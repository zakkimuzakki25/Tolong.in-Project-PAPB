package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileCarboViewModel : ViewModel() {

    private val _carbonData = MutableLiveData<Pair<Double, Double>>()
    val carbonData: LiveData<Pair<Double, Double>> = _carbonData

    init {
        fetchCarbonData()
    }

    private fun fetchCarbonData() {
        _carbonData.value = Pair(30.59, 780.0)
    }
}
