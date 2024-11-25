package com.example.domain.repository


import com.example.domain.models.Characters
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): kotlin.collections.List<Characters?>
}
