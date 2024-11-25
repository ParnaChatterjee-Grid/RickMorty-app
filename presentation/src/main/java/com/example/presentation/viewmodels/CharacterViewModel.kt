package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.models.Characters
import com.example.domain.usecases.GetCharactersUsecase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val getCharactersUseCase : GetCharactersUsecase) :ViewModel(){
    private val _charactersState =
        MutableStateFlow<ResultState<List<Characters?>>>(ResultState.Loading)
    val charactersState: StateFlow<ResultState<List<Characters?>>> = _charactersState

    init{
        getAllCharacters()
    }

    private fun getAllCharacters() {

        viewModelScope.launch(IO) {

            try {
                val result = getCharactersUseCase.invoke()
                _charactersState.emit(ResultState.Success(result))// Emit the updated list
            } catch (e: Exception) {
                // Handle error, emit empty list or error state
                _charactersState.emit(ResultState.Error(exception = e))
            }
        }
    }

}



