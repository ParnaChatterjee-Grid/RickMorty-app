package com.example.domain.repository

import com.example.domain.models.Character


interface CharacterRepository {
    suspend fun getCharacters(): kotlin.collections.List<Character?>
}
