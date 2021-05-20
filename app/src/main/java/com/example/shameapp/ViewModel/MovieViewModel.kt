package com.example.shameapp.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shameapp.Model.API.API
import com.example.shameapp.Model.API.Repository
import com.example.shameapp.Model.DataModels.CrewShowFolder.CrewShow
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.Model.DataModels.MovieDetailFolder.MovieDetail
import com.example.shameapp.Model.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(API())
    val firebase = FirebaseRepository()

    fun getMovieDetails(id: Int) : LiveData<MovieDetail>{
        var movie = MutableLiveData<MovieDetail>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getMovieDetails(id)
            movie.postValue(arrivedData)
        }
        return movie
    }

    fun getMovieCrew(movieID: Int) : LiveData<CrewShow>{
        var results = MutableLiveData<CrewShow>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getMovieCrew(movieID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun addMovieTVtoFirebase(movieTV: FirebaseMovieTV){

        //FirebaseDatabase.getInstance().getReference().child("To watch list").push().child("MovieTV").setValue()
        //firebase.addMovieTV(FirebaseMovieTV(1, toWatch = true, watched = false))//addGame(FirebaseGame(game.value!!.id, !isPlayed.value!!, isFav.value!!))
        //firebase.addMovieTV(FirebaseMovieTV(movieTV.value!!.ID, toWatch = true, watched = false))//addGame(FirebaseGame(game.value!!.id, !isPlayed.value!!, isFav.value!!))
        firebase.addMovieTV(movieTV)//addGame(FirebaseGame(game.value!!.id, !isPlayed.value!!, isFav.value!!))
    }

    fun changeMovieTVListInFirebase(movieTV: FirebaseMovieTV){
        firebase.changeMovieTVList(movieTV)
    }

    fun deleteMovieTVFromFirebase(movieTV: FirebaseMovieTV){
        firebase.deleteMovieTV(movieTV)
    }

    fun loadMovieTVList(listType: String): MutableList<FirebaseMovieTV> {

        Log.d("List3","a ${firebase.loadMovieTVList(listType)}")
        return firebase.loadMovieTVList(listType)
    }
}