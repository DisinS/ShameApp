package com.example.shameapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shameapp.Model.API.API
import com.example.shameapp.Model.API.Repository
import com.example.shameapp.Model.DataModels.CrewShowFolder.CrewShow
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.Model.DataModels.MovieDetailFolder.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(API())
    val firebase = FirebaseViewModel()

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

    /*fun loadMovieTVList(listType:String){//}: MutableLiveData<List<Int>> {//callback: FirebaseCallback, listType: String){//}: MutableList<FirebaseMovieTV> {



        //Log.d("List5","a ${firebase.loadMovieTVList(listType)}")
        firebase.loadMovieTVList(listType)
    }*/
}