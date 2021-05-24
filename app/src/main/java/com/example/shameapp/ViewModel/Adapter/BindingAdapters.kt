package com.example.shameapp.ViewModel.Adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}