package com.example.domain.usecases


import com.example.domain.models.CharacterSchema
import com.example.domain.repository.CharacterRepository
import com.example.domain.utils.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacters @Inject constructor(private val repository: CharacterRepository){
    suspend  fun getCharacters(): Flow<Results<List<CharacterSchema>>> {
        return repository.getCharacters()
    }

}