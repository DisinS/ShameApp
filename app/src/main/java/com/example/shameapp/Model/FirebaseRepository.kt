package com.example.shameapp.Model

import android.util.Log
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepository {
    private val firebaseDB : DatabaseReference = Firebase.database.reference
    private val auth : FirebaseAuth = Firebase.auth

    private val list = mutableListOf<FirebaseMovieTV>()

    private fun getAllUsersMovieTV() = firebaseDB.child(auth.currentUser!!.uid)

    fun addMovieTV(movieTV: FirebaseMovieTV){
        if (movieTV.toWatch)
            getAllUsersMovieTV().child("To watch list").child(movieTV.type).child(movieTV.id.toString()).setValue(movieTV)
        else
            getAllUsersMovieTV().child("Watched list").child(movieTV.type).child(movieTV.id.toString()).setValue(movieTV)
    }

    fun changeMovieTVList(movieTV: FirebaseMovieTV){
        if (!movieTV.watched)
            getAllUsersMovieTV().child("Watched list").child(movieTV.type).child(movieTV.id.toString()).removeValue()
        else
            getAllUsersMovieTV().child("To watch list").child(movieTV.type).child(movieTV.id.toString()).removeValue()
    }

    fun deleteMovieTV(movieTV: FirebaseMovieTV){
        getAllUsersMovieTV().child("Watched list").child(movieTV.type).child(movieTV.id.toString()).removeValue()
        getAllUsersMovieTV().child("To watch list").child(movieTV.type).child(movieTV.id.toString()).removeValue()
    }

    fun loadMovieTVList(listType: String): MutableList<FirebaseMovieTV> {
        //val list = mutableListOf<FirebaseMovieTV>()

        //readData(firebaseCallback FirebaseCallback, listType)

        /*Log.d("Before","Before")
        firebaseDB.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               // list.clear()
                for (row in snapshot.child(auth.currentUser!!.uid).child(listType).child("movie").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    list.add(newRow!!)
                    Log.d("Inside","Inside")
                    Log.d("List1","a ${list}")
                }
                for (row in snapshot.child(auth.currentUser!!.uid).child(listType).child("tv").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    list.add(newRow!!)
                    Log.d("List2","a ${list}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Reading value","Failed")
            }
        })
        Log.d("After","After")
        Log.d("List4","a ${list}")*/

        return list
    }

    private fun readData(firebaseCallback: FirebaseCallback, listType: String){
        //Log.d("Before","Before")
        firebaseDB.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // list.clear()
                for (row in snapshot.child(auth.currentUser!!.uid).child(listType).child("movie").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    list.add(newRow!!)
                    //Log.d("Inside","Inside")
                    //Log.d("List1","a ${list}")
                }
                for (row in snapshot.child(auth.currentUser!!.uid).child(listType).child("tv").children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    list.add(newRow!!)
                    //Log.d("List2","a ${list}")
                }

                firebaseCallback.onCallback(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Reading value","Failed")
            }
        })
        //Log.d("After","After")
    }

    private interface FirebaseCallback{
        fun onCallback(list: MutableList<FirebaseMovieTV>)
    }
}