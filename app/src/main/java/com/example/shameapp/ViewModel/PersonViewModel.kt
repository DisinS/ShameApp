package com.example.shameapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shameapp.Model.API.API
import com.example.shameapp.Model.API.Repository
import com.example.shameapp.Model.DataModels.PersonDetailFolder.PersonDetail
import com.example.shameapp.Model.DataModels.PersonMoviesTVsFolder.PersonMovieTVCredits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(API())

    fun getPersonDetails(id: Int) : LiveData<PersonDetail>{
        var person = MutableLiveData<PersonDetail>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getPersonDetails(id)
            person.postValue(arrivedData)
        }
        return person
    }

    fun getPersonMoviesTVs(person_id: Int) : LiveData<PersonMovieTVCredits>{
        var results = MutableLiveData<PersonMovieTVCredits>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getPersonMoviesTVs(person_id)
            results.postValue(arrivedData)
        }
        return results
    }
}