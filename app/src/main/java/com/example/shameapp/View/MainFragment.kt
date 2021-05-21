package com.example.shameapp.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.Model.DataModels.MovieViewClass
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
        Log.d("WATCHED", "${firebaseViewModel.listOfWatchedMovieTVs.value}")
        Log.d("TO WATCH", "${firebaseViewModel.listOfToWatchMovieTVs.value}")


        //setMovieClassAdapter(detailsForFirebaseList("To watch list"))

        //guziki przełączania
        //bottomNavigation


        return view
    }

    private fun detailsForFirebaseList(listType: String): MutableList<MovieViewClass> {
        var list = mutableListOf<MovieViewClass>()
        when (listType){
            "To watch list" -> {
                list = downloadDetailsForFirebaseList(firebaseViewModel.listOfToWatchMovieTVs)
            }
            "Watched list" -> {
                list = downloadDetailsForFirebaseList(firebaseViewModel.listOfWatchedMovieTVs)
            }
        }

        return list
    }

    private fun downloadDetailsForFirebaseList(list: MutableLiveData<List<FirebaseMovieTV>>): MutableList<MovieViewClass> {
        val newList = list.value
        val result = mutableListOf<MovieViewClass>()

        for (x in newList!!){
            when(x.type){
                "movie" -> {
                    var movieViewClass: MovieViewClass
                    movieViewModel.getMovieDetails(x.id).observe(viewLifecycleOwner, Observer { item ->
                        movieViewClass = MovieViewClass(item)
                        if (movieViewClass != null)
                            result.add(movieViewClass)
                    })
                }
                "tv" -> {
                    var movieViewClass: MovieViewClass
                    tvViewModel.getTVDetails(x.id).observe(viewLifecycleOwner, Observer { item ->
                        movieViewClass = MovieViewClass(item)
                        if (movieViewClass != null)
                            result.add(movieViewClass)
                    })
                }
            }
        }

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.setOnClickListener {
            val query = searchQueryInput.text.toString()
            searchQueryInput.text = null
            val action = MainFragmentDirections.actionMainFragmentToSearchResultsFragment(query)
            view.findNavController().navigate(action)


            //activity, R.id.shipping_host_nav
            //val navController =
                //Navigation.findNavController(getActivity(), R.id.shipping_host_nav)
            //val navController = activity?.let { it1 -> Navigation.findNavController(it1, R.id.fragmentMain) }
            //navController?.navigate(R.id.searchResultsFragment)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private fun setMovieClassAdapter(movieViewClassList: List<MovieViewClass>){
        val adapter = ListAdapter()
        val recyclerView = movieTVRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        adapter.setData(movieViewClassList)

    }


}

