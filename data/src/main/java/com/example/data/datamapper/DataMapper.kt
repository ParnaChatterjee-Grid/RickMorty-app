package com.example.data.datamapper

import com.example.data.GetAllCharactersQuery
import com.example.domain.models.Characters

fun getCharactersQueryToCharacterModel(results: GetAllCharactersQuery.Result?): Characters? {
    return results?.let {
        Characters(
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            image = it.image.orEmpty()
        )
    }
}
