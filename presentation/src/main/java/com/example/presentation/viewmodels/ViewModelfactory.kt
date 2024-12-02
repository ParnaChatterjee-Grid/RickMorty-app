package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetCharactersUsecase
import com.example.presentation.viewmodels.CharacterViewModel
import javax.inject.Inject

class ViewModelfactory @Inject constructor(
    private val getCharactersUseCase: GetCharactersUsecase) : ViewModelProvider.Factory{
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: java.lang.Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterViewModel::class.java) -> {
                CharacterViewModel(getCharactersUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

