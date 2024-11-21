package com.example.data.datamapper

import com.example.data.GetAllCharactersQuery
import com.example.domain.models.CharacterSchema

fun getCharactersQueryToCharacterModel(results: GetAllCharactersQuery.Result?): CharacterSchema? {
    return results?.let {
        CharacterSchema(
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            image = it.image.orEmpty()
        )
    }
}
