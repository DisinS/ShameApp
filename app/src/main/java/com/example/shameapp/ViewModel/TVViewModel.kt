package com.example.shameapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shameapp.Model.API.API
import com.example.shameapp.Model.API.Repository
import com.example.shameapp.Model.DataModels.CrewShowFolder.CrewShow
import com.example.shameapp.Model.DataModels.TVDetailFolder.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(API())

    fun getTVDetails(id: Int): LiveData<TV> {
        var tv = MutableLiveData<TV>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVDetails(id)
            tv.postValue(arrivedData)
        }
        return tv
    }

    fun getTVCrew(id: Int): LiveData<CrewShow> {
        var tv = MutableLiveData<CrewShow>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVCrew(id)
            tv.postValue(arrivedData)
        }
        return tv
    }
}