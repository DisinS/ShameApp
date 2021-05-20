package com.example.shameapp.Model.DataModels

data class FirebaseMovieTV(
    val type: String = "",
    val id: Int = 0,
    val title: String = "",
    val toWatch: Boolean = false,
    val watched: Boolean = false
)
