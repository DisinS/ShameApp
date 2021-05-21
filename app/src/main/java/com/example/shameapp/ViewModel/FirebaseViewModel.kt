package com.example.shameapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseViewModel : ViewModel() {
    private val firebaseDB : DatabaseReference = Firebase.database.reference
    private val auth : FirebaseAuth = Firebase.auth

    val watched = "Watched list"
    val toWatch = "To watch list"

    val listOfWatchedMovieTVs = MutableLiveData<List<FirebaseMovieTV>>()
    val listOfToWatchMovieTVs = MutableLiveData<List<FirebaseMovieTV>>()

    fun setWatchedMovieTVList(list : List<FirebaseMovieTV>){
        listOfWatchedMovieTVs.value = list
    }

    fun setToWatchMovieTVList(list : List<FirebaseMovieTV>){
        listOfToWatchMovieTVs.value = list
    }


    fun getAllUsersMovieTV() = firebaseDB.child(auth.currentUser!!.uid)

    fun addMovieTV(movieTV: FirebaseMovieTV){
        if (movieTV.toWatch)
            getAllUsersMovieTV().child(toWatch).child(movieTV.type).child(movieTV.id.toString()).setValue(movieTV)
        else
            getAllUsersMovieTV().child(watched).child(movieTV.type).child(movieTV.id.toString()).setValue(movieTV)
    }

    fun changeMovieTVList(movieTV: FirebaseMovieTV){
        if (!movieTV.watched)
            getAllUsersMovieTV().child(watched).child(movieTV.type).child(movieTV.id.toString()).removeValue()
        else
            getAllUsersMovieTV().child(toWatch).child(movieTV.type).child(movieTV.id.toString()).removeValue()
    }

    fun deleteMovieTV(movieTV: FirebaseMovieTV){
        getAllUsersMovieTV().child(watched).child(movieTV.type).child(movieTV.id.toString()).removeValue()
        getAllUsersMovieTV().child(toWatch).child(movieTV.type).child(movieTV.id.toString()).removeValue()
    }

    fun downloadMovieTVList(){ //usunąć argument i dodać drugą liste
        getAllUsersMovieTV().addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listToWatch = mutableListOf<FirebaseMovieTV>()
                val listOfWatched = mutableListOf<FirebaseMovieTV>()

                for (row in snapshot.child(toWatch).child("movie").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    if (newRow != null)
                        listToWatch.add(newRow!!)
                }
                for (row in snapshot.child(toWatch).child("tv").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    if (newRow != null)
                        listToWatch.add(newRow!!)
                }
                Log.d("To watch list", "${listToWatch}")

                for (row in snapshot.child(watched).child("movie").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    if (newRow != null)
                        listToWatch.add(newRow!!)
                }
                for (row in snapshot.child(watched).child("tv").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    if (newRow != null)
                        listToWatch.add(newRow!!)
                }
                Log.d("Watched list", "${listOfWatched}")

                setToWatchMovieTVList(listToWatch)
                setWatchedMovieTVList(listOfWatched)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Reading value","Failed ${error}")
            }
        })
    }
}