package com.example.shameapp.Model.API

import com.example.shameapp.Model.DataModels.CrewShowFolder.CrewShow
import com.example.shameapp.Model.DataModels.MovieDetailFolder.MovieDetail
import com.example.shameapp.Model.DataModels.PersonDetailFolder.PersonDetail
import com.example.shameapp.Model.DataModels.PersonMoviesTVsFolder.PersonMovieTVCredits
import com.example.shameapp.Model.DataModels.SearchModelFolder.SearchMovie
import com.example.shameapp.Model.DataModels.SearchModelFolder.SearchPeople
import com.example.shameapp.Model.DataModels.SearchModelFolder.SearchTv
import com.example.shameapp.Model.DataModels.TVDetailFolder.TV
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_key: String = "?api_key=7424044fa22991835d835d229b30d0d7"  // poprzednie "?api_key=fbfd2d53b7504d595ee9c450e52d4026"
//Zostawmy angielski bo wszystko działa, w polskim pokazuje z połowe rzeczy
const val LANGUAGE: String = "en-US"

interface API {
    //API: fbfd2d53b7504d595ee9c450e52d4026
    //Zapytania: https://developers.themoviedb.org/3/getting-started/introduction
    //JSON na klasy w kotlin: https://www.json2kotlin.com/
    //Przetwarzanie obrazów: https://developers.themoviedb.org/3/getting-started/images


    /*Informacje o filmie
    https://developers.themoviedb.org/3/movies/get-movie-details
    movie_id => id filmu
    language => język odpowiedzi, załóżmy że bazowym będzie pl-PL
     */
    @GET("movie/{movieID}${API_key}&language=${LANGUAGE}")
    suspend fun getMovieDetails(
        @Path("movieID") movieID: Int
    ): Response<MovieDetail>

    //Informacja o serii TV
    @GET("tv/{id}${API_key}&language=${LANGUAGE}")
    suspend fun getTVDetails(
        @Path("id") id:Int
    ) : Response<TV>

    //Informacje o osobie
    @GET("person/{person_id}${API_key}&language=${LANGUAGE}")
    suspend fun getPersonDetail(
        @Path("person_id") person_id: Int
    ) : Response<PersonDetail>

    //Filmy / seriale w których aktor występuje
    @GET("person/{person_id}/combined_credits${API_key}")
    suspend fun getPersonMoviesTVs(
        @Path("person_id") person_id: Int
    ) : Response<PersonMovieTVCredits>

    //Aktorzy grający w danym filmie
    //https://developers.themoviedb.org/3/movies/get-movie-credits
    @GET("movie/{movieID}/credits${API_key}")
    suspend fun getMovieCrew(
        @Path("movieID") movieID: Int
    ): Response<CrewShow>

    //Aktorzy grający w danym serialu
    //https://developers.themoviedb.org/3/movies/get-movie-credits
    @GET("tv/{tvID}/credits${API_key}")
    suspend fun getTVCrew(
        @Path("tvID") tvID: Int
    ): Response<CrewShow>

    /*Wyszukiwanie filmów
    query => fraza do wyszukania
    */
    @GET("search/movie${API_key}&language=${LANGUAGE}")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchMovie>
    /*Wyszukiwanie osób
    query => fraza do wyszukania
    */
    @GET("search/person${API_key}&language=${LANGUAGE}")
    suspend fun searchPeople(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchPeople>
    /*Wyszukiwanie seriali
    query => fraza do wyszukania
    */
    @GET("search/tv${API_key}&language=${LANGUAGE}")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<SearchTv>

    companion object {
        operator fun invoke(): API {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()
                .create(API::class.java)
        }
    }
}