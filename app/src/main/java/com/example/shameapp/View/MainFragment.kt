package com.example.shameapp.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shameapp.ViewModel.FirebaseViewModel
import com.example.shameapp.R
import com.example.shameapp.ViewModel.Adapter.ListAdapter
import com.example.shameapp.ViewModel.MovieViewModel
import com.example.shameapp.ViewModel.TVViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var tvViewModel : TVViewModel
    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        tvViewModel = ViewModelProvider(this).get(TVViewModel::class.java)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        firebaseViewModel.downloadMovieTVList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieClassAdapter(firebaseViewModel.toWatch)

        buttonSearch.setOnClickListener {
            val query = searchQueryInput.text.toString()
            searchQueryInput.text = null
            val action = MainFragmentDirections.actionMainFragmentToSearchResultsFragment(query)
            view.findNavController().navigate(action)
        }

        switchList.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                setMovieClassAdapter(firebaseViewModel.watched)
                listName.text = getString(R.string.watched)
                imageToWatchList.visibility = View.INVISIBLE
                imageWachedList.visibility = View.VISIBLE
            }
            else{
                setMovieClassAdapter(firebaseViewModel.toWatch)
                listName.text = getString(R.string.toWatch)
                imageToWatchList.visibility = View.VISIBLE
                imageWachedList.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private fun setMovieClassAdapter(listType: String){
        when(listType){
            "To watch list" ->{
                firebaseViewModel.listOfToWatchMovieTVs.observe(viewLifecycleOwner, Observer { item ->
                    val adapter = ListAdapter()
                    val recyclerView = movieTVRecyclerView
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    adapter.setData(item)
                })
            }
            "Watched list" -> {
                firebaseViewModel.listOfWatchedMovieTVs.observe(viewLifecycleOwner, Observer { item ->
                    val adapter = ListAdapter()
                    val recyclerView = movieTVRecyclerView
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    adapter.setData(item)
                })
            }
        }
    }
}

