package com.example.domain.repository

import com.example.domain.CharacterSchema
import com.example.domain.ResultState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharactersList(): Flow<ResultState<List<CharacterSchema>>>
    fun getCharacter(id: String): Flow<ResultState<CharacterSchema>>
}
