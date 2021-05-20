package com.example.shameapp.Model

import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepository {
    private val firebaseDB : DatabaseReference = Firebase.database.reference
    private lateinit var databaseReference: DatabaseReference //= Firebase.database.reference
    private val auth : FirebaseAuth = Firebase.auth

    private fun getAllUsersMovieTV() = firebaseDB.child(auth.currentUser!!.uid)

    fun firstAdd(){
        val fireBase = FirebaseDatabase.getInstance()
        databaseReference = fireBase.getReference("MovieTVData")
    }

    fun addMovieTV(movieTV: FirebaseMovieTV){
        //firstAdd()
        getAllUsersMovieTV().child(movieTV.id.toString()).setValue(movieTV)
    }
}