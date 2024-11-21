package com.example.domain.usecases


import com.example.domain.models.CharacterSchema
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUsecase @Inject constructor(private val repository: CharacterRepository){
    suspend  fun invoke(): Flow<com.example.domain.utils.Results<List<CharacterSchema?>>> {
        return repository.getCharacters()
    }
}