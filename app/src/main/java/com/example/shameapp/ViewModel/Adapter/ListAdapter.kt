package com.example.shameapp.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.R

class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"
    private var list = emptyList<FirebaseMovieTV>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_row, parent, false)
        )

        return view
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

/*
class FavouriteAdapter() : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = favouriteList[position]
        bindImage(holder.itemView.favouritePoster, imageSource + currentItem.posterPath)
        holder.itemView.favouriteTitle.text = currentItem.title
        holder.itemView.favouriteDescription.text = currentItem.movieDescription

        if(currentItem.toWatch){
            holder.itemView.labelIcon.setImageResource(R.drawable.ic_eye_true)
        } else holder.itemView.labelIcon.visibility = View.GONE

        holder.itemView.setOnClickListener {
            var helperClass = HelperClass(currentItem.itemID, currentItem.type)
            val action = FavouriteListDirections.actionFavouriteListToMovieView(helperClass, currentItem.title)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<Show>) {
        this.favouriteList = data
        notifyDataSetChanged()
    }
}



 */