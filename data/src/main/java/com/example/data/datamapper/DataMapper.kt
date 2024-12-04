package com.example.data.datamapper

import com.example.data.GetAllCharactersQuery
import com.example.data.GetCharacterByIdQuery
import com.example.domain.models.CharacterDetails
import com.example.domain.models.Characters
import com.example.domain.models.Episode
import com.example.domain.models.Locations
import com.example.domain.models.Origins

fun getCharactersQueryToCharacterModel(results: GetAllCharactersQuery.Result?): Characters? {
    return results?.let {
        Characters(
            id = it.id,
            name = it.name,
            image = it.image
        )
    }
}
fun getCharacterDetailsToCharacterDetails(results: GetCharacterByIdQuery.Character):CharacterDetails?{
    return results?.let {
        CharacterDetails(
            id = it.id,
            name = it.name,
            image = it.image,
            status = it.status,
            species = it.species,
            gender = it.gender,
            origin = getOrigin(it.origin),
            episodes = getEpisodes(it.episode) ,
            locations = getLocations(it.location),
        )
    }
}

fun getLocations(location: GetCharacterByIdQuery.Location): Locations? {
    return location.id?.let { Locations(it,location.name, location.dimension) }
}

fun getEpisodes(episodes: List<GetCharacterByIdQuery.Episode>): List<Episode> {

        var episodeslist = mutableListOf<Episode>()
        for(episode in episodes) {
            if (episode != null) {
                episodeslist.add(
                    Episode(
                        episode.id,
                        episode.name,
                        episode.air_date,
                        episode.episode

                    )
                )
            }
        }
        return episodeslist
    }


fun getOrigin(origin: GetCharacterByIdQuery.Origin):Origins{
    return Origins(
        id = origin.id,
        type = origin.type,
        name = origin.name,
        dimension = origin.dimension,
    )}
