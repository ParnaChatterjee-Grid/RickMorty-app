package com.example.presentation.charviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetAllCharacterUsecase
import com.example.domain.usecase.GetSingleCharacterUsecase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val getallCharactersUseCase: GetAllCharacterUsecase,
    private val getCharacterUseCase: GetSingleCharacterUsecase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharViewmodels::class.java) -> {
                CharViewmodels(getallCharactersUseCase,getCharacterUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
