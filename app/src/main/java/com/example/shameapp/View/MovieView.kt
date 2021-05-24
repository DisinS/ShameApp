package com.example.shameapp.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shameapp.Model.DataModels.CrewShowFolder.CrewShow
import com.example.shameapp.Model.DataModels.FirebaseMovieTV
import com.example.shameapp.Model.DataModels.MovieViewClass
import com.example.shameapp.R
import com.example.shameapp.ViewModel.Adapter.ActorAdapter
import com.example.shameapp.ViewModel.Adapter.bindImage
import com.example.shameapp.ViewModel.FirebaseViewModel
import com.example.shameapp.ViewModel.MovieViewModel
import com.example.shameapp.ViewModel.TVViewModel
import kotlinx.android.synthetic.main.fragment_movie_view.*
import kotlinx.android.synthetic.main.fragment_movie_view.view.*


const val imageSource: String = "https://image.tmdb.org/t/p/w500"

class MovieView : Fragment() {

    private val args by navArgs<MovieViewArgs>()
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mTVViewModel: TVViewModel
    private lateinit var firebaseViewModel: FirebaseViewModel

    private lateinit var movieClass: MovieViewClass

    private lateinit var typeOfMovie: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_view, container, false)
        mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        mTVViewModel = ViewModelProvider(this).get(TVViewModel::class.java)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        typeOfMovie = args.movieInfo.type

        when (typeOfMovie) {
            "movie" -> {
                mMovieViewModel.getMovieDetails(args.movieInfo.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        movieClass = MovieViewClass(item)
                        calculateRating(item.vote_average)
                        calculateTime(item.runtime)
                        completeUI()
                    })
            }
            "tv" -> {
                mTVViewModel.getTVDetails(args.movieInfo.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        movieClass = MovieViewClass(item)
                        calculateRating(item.vote_average)
                        calculateTime(item.episode_run_time)
                        completeUI()
                    })
            }
            else -> {
                Toast.makeText(requireContext(), getString(R.string.dataError), Toast.LENGTH_LONG).show()
            }
        }

        CompleteCrewList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.addToToWatchListButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieClass.title,
                true,
                false,
                movieClass.movieDescription,
                movieClass.releaseDate,
                movieClass.posterPath
            )
            firebaseViewModel.addMovieTV(firebaseMovieTV)
            firebaseViewModel.changeMovieTVList(firebaseMovieTV)
            Toast.makeText(activity,"Added to list of 'To watch' movies!", Toast.LENGTH_SHORT).show()
        }

        view.addToWatchedListButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieClass.title,
                false,
                true,
                movieClass.movieDescription,
                movieClass.releaseDate,
                movieClass.posterPath
            )
            Log.d("MovieViewClass", "${movieClass}")
            firebaseViewModel.addMovieTV(firebaseMovieTV)
            firebaseViewModel.changeMovieTVList(firebaseMovieTV)
            Toast.makeText(activity,"Added to list of 'Watched' movies!", Toast.LENGTH_SHORT).show()
        }

        view.removeFromListsButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieClass.title,
                false,
                false,
                movieClass.movieDescription,
                movieClass.releaseDate,
                movieClass.posterPath
            )
            firebaseViewModel.deleteMovieTV(firebaseMovieTV)
            Toast.makeText(activity,"Remove from lists!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = MovieView()
    }

    private fun completeUI() {
        if (::movieClass.isInitialized) {
            bindImage(backgroundImage, imageSource + movieClass.backgroundPath)
            bindImage(posterImage, imageSource + movieClass.posterPath)
            movieTitle.text = movieClass.title
            movieTagline.text = movieClass.tagline
            yearRelease.text = "(${movieClass.releaseDate.split("-")[0]})"
            dateRelease.text = movieClass.releaseDate
            movieType.text = movieClass.movieType
            movieDescription.text = movieClass.movieDescription
        } else {
            Toast.makeText(requireContext(), "Klasa nie zainicjowana", Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateTime(time: List<Int>) {
        if (time.size == 3)
            movieTime.text = "${time[0]}h ${time[1]}m ${time[2]}s"
        else if (time.size == 2)
            movieTime.text = "${time[0]}m ${time[1]}s"
        else if (time.size == 1)
            movieTime.text = "${time[0]}m"
        else {
            movieTime.text = "Pusto"
        }
    }

    private fun calculateTime(time: Int) {
        var minutes = time % 60
        var hours = (time - (time % 60)) / 60
        movieTime.text = "${hours}h ${minutes}m"
    }

    private fun calculateRating(voteAverate: Double) {
        var rating: Int = (voteAverate * 10).toInt()
        if (rating == 0)
            movieRatingText.text = "NR"
        else
            movieRatingText.text = "${rating}%"
        movieRating.progress = rating
    }

    private fun CompleteCrewList(){
        when(args.movieInfo.type){
            "movie" ->{
                mMovieViewModel.getMovieCrew(args.movieInfo.ID).observe(viewLifecycleOwner, Observer { items->
                    if(items != null && items.cast.isNotEmpty()){
                        AddCrewToRecycler(items)
                    }
                    else{
                        textActors.visibility = View.GONE
                    }
                })
            }
            "tv" ->{
                mTVViewModel.getTVCrew(args.movieInfo.ID).observe(viewLifecycleOwner, Observer { items->
                    if(items != null && items.cast.isNotEmpty()){
                        AddCrewToRecycler(items)
                    }
                    else{
                        textActors.visibility = View.GONE
                    }
                })
            }
            else ->{
                Toast.makeText(requireContext(), "Błąd rodzaju filmu/serialu", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun AddCrewToRecycler(crewShow: CrewShow){
        val adapter = ActorAdapter()
        val recyclerView = crewRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter.setData(crewShow.cast)
    }
}