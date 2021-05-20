package com.example.shameapp.Model.DataModels.SearchModelFolder

data class SearchPeople(
    val page: Int,
    val results: List<PeopleSearchResult>,
    val total_results: Int,
    val total_pages: Int
)