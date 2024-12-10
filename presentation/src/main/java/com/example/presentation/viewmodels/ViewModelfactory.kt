package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.di.IoDispatcher
import com.example.domain.usecases.GetCharactersUsecase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ViewModelfactory @Inject constructor(
    private val getCharactersUseCase: GetCharactersUsecase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterViewModel::class.java) -> {
                CharacterViewModel(getCharactersUseCase,coroutineDispatcher) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

