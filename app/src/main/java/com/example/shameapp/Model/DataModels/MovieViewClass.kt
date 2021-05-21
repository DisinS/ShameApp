package com.example.shameapp.Model.DataModels

import com.example.shameapp.Model.DataModels.MovieDetailFolder.Genres
import com.example.shameapp.Model.DataModels.MovieDetailFolder.MovieDetail
import com.example.shameapp.Model.DataModels.TVDetailFolder.TV

//import com.example.moviebase.Model.Database.Show

public class MovieViewClass {
    val ID: Int
    val type: String
    val posterPath: String
    val backgroundPath: String
    val title: String
    val tagline: String
    val releaseDate: String
    val movieType: String
    val movieDescription: String

    public constructor(movieData: MovieDetail){
        this.ID = movieData.id
        this.type = "movie"
        this.posterPath = movieData.poster_path
        this.backgroundPath = movieData.backdrop_path
        this.title = movieData.title
        this.tagline = movieData.tagline
        this.releaseDate = movieData.release_date
        this.movieType = createMovieType(movieData.genres)
        this.movieDescription = movieData.overview
    }

    public constructor(tvData: TV){
        this.ID = tvData.id
        this.type = "tv"
        this.posterPath = tvData.poster_path
        this.backgroundPath = tvData.backdrop_path
        this.title = tvData.name
        this.tagline = tvData.tagline
        this.releaseDate = tvData.first_air_date
        this.movieType = createMovieType(tvData.genres)
        this.movieDescription = tvData.overview
    }

    private fun createMovieType(genres: List<Genres>) : String{
        var types: StringBuilder = StringBuilder()
        for (x in genres) {
            types.append("${x.name} ")
        }
        return types.toString()
    }
}