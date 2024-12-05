package com.example.domain.models

data class CharacterDetails(
    val id: String,
    val name: String,
    val image: String,
    val status: String?,
    val species: String?,
    val gender: String?,
    val origin: Origins?,
    val episodes: List<Episode>,
    val locations: Locations?

)
data class Origins(
    var id: String = "",
    val type: String,
    val name: String,
    val dimension: String
)

data class Episode(
    val id : String,
    val name : String,
    val airdate : String,
    val episode : String

)

data class Locations(
    val id	: String,
    val name :	String,
    val dimension : String
)
