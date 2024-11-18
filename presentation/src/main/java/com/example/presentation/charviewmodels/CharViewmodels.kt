package com.example.presentation.charviewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CharacterSchema
import com.example.domain.ResultState
import com.example.domain.usecase.GetAllCharacterUsecase
import com.example.domain.usecase.GetSingleCharacterUsecase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharViewmodels @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharacterUsecase,
    private val getCharacterUseCase: GetSingleCharacterUsecase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

) : ViewModel() {

    private val _charactersStateMut =
        MutableStateFlow<ResultState<List<CharacterSchema>>>(ResultState.Loading)
    val charactersState: StateFlow<ResultState<List<CharacterSchema>>> = _charactersStateMut



    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch(coroutineDispatcher) {
            getAllCharactersUseCase.invoke()
                .catch { _charactersStateMut.emit(ResultState.Error(it)) }
                .collect { result ->
                    _charactersStateMut.emit(result)
                }
        }
    }


}