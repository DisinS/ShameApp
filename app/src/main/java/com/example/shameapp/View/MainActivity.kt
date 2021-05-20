package com.example.shameapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.shameapp.R
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //FirebaseDatabase.getInstance().getReference().child("To watch list").child("MovieTV").setValue("abcd")
        //FirebaseDatabase.getInstance().getReference().child("Add").setValue("abcdLOGIN")
    }


}