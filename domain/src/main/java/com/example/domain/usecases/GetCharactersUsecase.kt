package com.example.domain.usecases


import com.example.domain.models.Character
import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUsecase @Inject constructor(private val repository: CharacterRepository){
    suspend  fun invoke(): List<Character?> {
        return repository.getCharacters()
    }
}
