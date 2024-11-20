package com.example.domain.repository

import com.example.domain.models.CharacterSchema
import com.example.domain.utils.Results
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): Flow<Results<List<CharacterSchema>>>
}
