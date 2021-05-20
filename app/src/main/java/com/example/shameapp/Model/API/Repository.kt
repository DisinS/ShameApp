package com.example.shameapp.Model.API

class Repository(private val API: API) : SafeApiRequest() {
    suspend fun searchMovies(query: String, page: Int) = apiRequest {
        API.searchMovies(query, page)
    }

    suspend fun searchTv(query: String, page: Int) = apiRequest {
        API.searchTv(query, page)
    }

    suspend fun searchPeople(query: String, page: Int) = apiRequest {
        API.searchPeople(query, page)
    }

    suspend fun getMovieDetails(id: Int) = apiRequest {
        API.getMovieDetails(id)
    }

    suspend fun getTVDetails(id: Int) = apiRequest {
        API.getTVDetails(id)
    }

    suspend fun getPersonDetails(id: Int) = apiRequest {
        API.getPersonDetail(id)
    }

    suspend fun getMovieCrew(movieID: Int) = apiRequest {
        API.getMovieCrew(movieID)
    }

    suspend fun getTVCrew(tvID: Int) = apiRequest{
        API.getTVCrew(tvID)
    }

    suspend fun getPersonMoviesTVs(person_id: Int) = apiRequest {
        API.getPersonMoviesTVs(person_id)
    }
}