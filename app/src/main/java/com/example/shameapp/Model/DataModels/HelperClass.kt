package com.example.shameapp.Model.DataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HelperClass(
    val ID: Int,
    val type: String
) : Parcelable
