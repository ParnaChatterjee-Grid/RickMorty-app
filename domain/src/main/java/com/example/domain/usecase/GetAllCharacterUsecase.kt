package com.example.domain.usecase

import com.example.domain.CharacterSchema
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetAllCharacterUsecase(private val repository: CharacterRepository) {
    suspend operator fun invoke(): Flow<com.example.domain.ResultState<List<CharacterSchema>>> {
        return repository.getCharactersList()
    }
}