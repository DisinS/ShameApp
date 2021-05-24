package com.example.shameapp.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.Model.DataModels.HelperClass
import com.example.shameapp.R
import com.example.shameapp.View.MainFragmentDirections
import kotlinx.android.synthetic.main.search_result_row.view.*
import java.text.SimpleDateFormat
import java.util.*

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
        if(list[position].posterPath.isNullOrEmpty())
            holder.itemView.itemImage.setImageResource(R.drawable.ic_baseline_movie_24)
        else
            bindImage(holder.itemView.itemImage, imageSource + list[position].posterPath)

        holder.itemView.itemName.text = list[position].title
        holder.itemView.itemOverview.text = list[position].description

        val dateVals = list[position].releaseDate.split('-')
        if(dateVals.size == 3){
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, dateVals[0].toInt())
            calendar.set(Calendar.MONTH, dateVals[1].toInt())
            calendar.set(Calendar.DAY_OF_MONTH, dateVals[2].toInt())
            holder.itemView.itemDate.text = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault()).format(calendar.time)
        } else
            holder.itemView.itemDate.visibility = View.GONE

        holder.itemView.setOnClickListener {
            var helperClass = HelperClass(list[position].id, list[position].type)
            val action = MainFragmentDirections.actionMainFragmentToMovieView(helperClass, list[position].title)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(data: List<FirebaseMovieTV>) {
        this.list = data
        notifyDataSetChanged()
    }
}

