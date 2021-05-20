package com.example.shameapp.View

//import com.example.shameapp.Model.DataModels.MovieVideosFolder.Results

//import com.example.shameapp.ViewModel.ShowViewModel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
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
import com.example.shameapp.ViewModel.MovieViewModel
import com.example.shameapp.ViewModel.TVViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_movie_view.*
import kotlinx.android.synthetic.main.fragment_movie_view.view.*


const val imageSource: String = "https://image.tmdb.org/t/p/w500"

class MovieView : Fragment() {

    private val args by navArgs<MovieViewArgs>()
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mTVViewModel: TVViewModel

    //private lateinit var mShowViewModel: ShowViewModel

    private lateinit var movieClass: MovieViewClass
    var buttonContainerClicked : Boolean  = false;
    private var isFavourite = false
    private var isToWatch = false
    private var showExist = false

    private lateinit var typeOfMovie: Any

    private lateinit var showButtonAnimation : Animation
    private lateinit var hideButtonAnimation : Animation
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

        //mShowViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)

        //showButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.show_animation)
        //hideButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.hide_animation)

        //checkSetting(args.movieInfo.ID)
        //var movieType = args.movieInfo.type
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


        /*mMovieViewModel.movieTV.observe(viewLifecycleOwner, Observer {
            mMovieViewModel.getMovieDetails(args.movieInfo.ID)
        })*/

        /*view.favouriteButton.setOnClickListener {
            if (::movieClass.isInitialized) {
                mShowViewModel.checkExist(movieClass.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        if(item > 0){
                            if (isFavourite) {
                                //USUWAMY
                                if(!!isFavourite && !isToWatch){
                                    mShowViewModel.deleteFavourite(movieClass.ID)
                                }
                                else{
                                    mShowViewModel.changeFavourite(movieClass.ID, false)
                                }
                                isFavourite = false
                                favouriteButton.setImageResource(R.drawable.ic_star)
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} ${getString(R.string.removedFromList)} ${getString(R.string.favourite)}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                //DODAJEMY
                                mShowViewModel.changeFavourite(movieClass.ID, true)
                                favouriteButton.setImageResource(R.drawable.ic_star_true)
                                isFavourite = true
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} ${getString(R.string.addedToList)} ${getString(R.string.favourite)}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        else{
                            //NIEISTNIEJE
                            mShowViewModel.addFavourite(movieClass.createFavourite(!isFavourite, isToWatch))
                            favouriteButton.setImageResource(R.drawable.ic_star_true)
                            isFavourite = true
                            Toast.makeText(
                                requireContext(),
                                "${movieClass.title} ${getString(R.string.addedToList)} ${getString(R.string.favourite)}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
            }
        }*/

        /*view.toWatchButton.setOnClickListener {
            if (::movieClass.isInitialized) {
                mShowViewModel.checkExist(movieClass.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        if(item > 0){
                            if (isToWatch) {
                                //USUWAMY
                                if(!!isToWatch && !isFavourite){
                                    mShowViewModel.deleteFavourite(movieClass.ID)
                                }
                                else{
                                    mShowViewModel.changeToWatch(movieClass.ID, false)
                                }
                                isToWatch = false
                                toWatchButton.setImageResource(R.drawable.ic_eye)
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} ${getString(R.string.removedFromList)} ${getString(R.string.toWatch)}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                //DODAJEMY
                                mShowViewModel.changeToWatch(movieClass.ID, true)
                                toWatchButton.setImageResource(R.drawable.ic_eye_true)
                                isToWatch = true
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} ${getString(R.string.addedToList)} ${getString(R.string.toWatch)}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        else{
                            //NIEISTNIEJE
                            mShowViewModel.addFavourite(movieClass.createFavourite(isFavourite, !isToWatch))
                            toWatchButton.setImageResource(R.drawable.ic_eye_true)
                            isToWatch = true
                            Toast.makeText(
                                requireContext(),
                                "${movieClass.title} ${getString(R.string.addedToList)} ${getString(R.string.toWatch)}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
            }
        }*/

        /*view.containerButton.setOnClickListener {
            buttonContainerClicked = !buttonContainerClicked;
            ChangeButtonsStyle(buttonContainerClicked)
        }*/

        CompleteCrewList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.addToWatchedListButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieTitle.text.toString(),
                false,
                true,
            )
            mMovieViewModel.addMovieTVtoFirebase(firebaseMovieTV)
            mMovieViewModel.changeMovieTVListInFirebase(firebaseMovieTV)
        }

        view.addToToWatchListButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieTitle.text.toString(),
                true,
                false,
            )
            mMovieViewModel.addMovieTVtoFirebase(firebaseMovieTV)
            mMovieViewModel.changeMovieTVListInFirebase(firebaseMovieTV)



            //Log.d("Guzik", "KLIK")
            //mMovieViewModel.addToWatchList()
            //FirebaseDatabase.getInstance().reference.child("To watch list").child(args.movieInfo.ID.toString()).setValue(movieTitle.text.toString())

            //FirebaseDatabase.getInstance().getReference().child("Add").setValue("abcdLOGIN")
            /*Firebase.database.reference.child("Test").addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("Test", "Success")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Test", "Cancel")
                }
            })*/
        }

        /*view.removeFromListsButton.setOnClickListener {
            val firebaseMovieTV = FirebaseMovieTV(
                args.movieInfo.type,
                args.movieInfo.ID,
                movieTitle.text.toString(),
                false,
                false,
            )
            mMovieViewModel.deleteMovieTVFromFirebase(firebaseMovieTV)
        }*/

        //var list2 = ArrayList<FirebaseMovieTV>()

        view.removeFromListsButton.setOnClickListener {

            val list = mMovieViewModel.loadMovieTVList("To watch list")
            Log.d("List","a ${list}")
            /*Firebase.database.reference.addValueEventListener(object: ValueEventListener {
                val list = mutableListOf<FirebaseMovieTV>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    // var value = snapshot.getValue(String::class.java)
                    /*list.clear()
                    for (row in snapshot.children){
                        val newRow = row.getValue(FirebaseMovieTV::class.java)
                        list.add(newRow!!)
                    }
                    Log.d("COS","e${list}")*/

                    for (row in snapshot.child("uqm9BFIb1ZXz9oFVwLm1yd4mNF53").child("To watch list").child("movie").children){
                        val newRow = row.getValue(FirebaseMovieTV::class.java)
                        Log.d("COS","e${newRow}")
                        list.add(newRow!!)
                    }
                    Log.d("COS","e${list2}")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Reading value","Failed")
                }
            })*/
        }
        /*
        fun loadMovieTVList(){
        firebaseDB.addValueEventListener(object: ValueEventListener{
            val list = mutableListOf<FirebaseMovieTV>()
            override fun onDataChange(snapshot: DataSnapshot) {
               // var value = snapshot.getValue(String::class.java)
                list.clear()
                for (row in snapshot.children){
                    val newRow = row.getValue(FirebaseMovieTV::class.java)
                    list.add(newRow!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Reading value","Failed")
            }
        })
    } */
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

    /*private fun checkSetting(ID: Int) {
        if (::mShowViewModel.isInitialized) {
            mShowViewModel.checkExist(ID)
                .observe(viewLifecycleOwner, Observer { item ->
                    if (item > 0) {
                        //ISTNIEJE
                        showExist = true
                        mShowViewModel.checkFavourite(ID).observe(viewLifecycleOwner, Observer { value->
                            //Log.d("komunikat", value.toString())
                            if(value){
                                isFavourite = true
                                favouriteButton.setImageResource(R.drawable.ic_star_true)
                            }
                            else
                                favouriteButton.setImageResource(R.drawable.ic_star)
                        })

                        mShowViewModel.checkToWatch(ID).observe(viewLifecycleOwner, Observer { value->
                            if(value){
                                isToWatch = true
                                toWatchButton.setImageResource(R.drawable.ic_eye_true)
                            }
                            else
                                toWatchButton.setImageResource(R.drawable.ic_eye)
                        })

                    } else {
                        //NIEISTNIEJE
                        showExist = false
                        favouriteButton.setImageResource(R.drawable.ic_star)
                        toWatchButton.setImageResource(R.drawable.ic_eye)
                    }
                })
        }
    }*/

    /*private fun ChangeButtonsStyle(clicked: Boolean){
        if(clicked){
            //Pokaz
            favouriteButton.visibility = View.VISIBLE
            toWatchButton.visibility = View.VISIBLE

            favouriteButton.startAnimation(showButtonAnimation)
            toWatchButton.startAnimation(showButtonAnimation)

            favouriteButton.isClickable = true
            toWatchButton.isClickable = true
        }
        else{
            //Schowaj
            favouriteButton.visibility = View.INVISIBLE
            toWatchButton.visibility = View.INVISIBLE

            favouriteButton.startAnimation(hideButtonAnimation)
            toWatchButton.startAnimation(hideButtonAnimation)

            favouriteButton.isClickable = false
            toWatchButton.isClickable = false
        }
    }*/

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