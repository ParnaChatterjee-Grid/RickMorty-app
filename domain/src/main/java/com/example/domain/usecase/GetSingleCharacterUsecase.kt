package com.example.domain.usecase

import com.example.domain.CharacterSchema
import com.example.domain.ResultState
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetSingleCharacterUsecase(private val repository: CharacterRepository) {
    suspend operator fun invoke(id: String): Flow<ResultState<CharacterSchema>>
    {
        return repository.getCharacter(id)
    }
}