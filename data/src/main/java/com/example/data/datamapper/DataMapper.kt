package com.example.data.datamapper

import com.example.data.GetAllCharactersQuery
import com.example.domain.models.Character

fun getCharactersQueryToCharacterModel(results: GetAllCharactersQuery.Result?): Character? {
    return results?.let {
        Character(
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            image = it.image.orEmpty()
        )
    }
}
