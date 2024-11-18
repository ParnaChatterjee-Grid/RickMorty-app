package com.example.presentation.charviewmodels

import com.example.domain.usecase.GetAllCharacterUsecase
import com.example.domain.usecase.GetSingleCharacterUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelDiModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        getCharactersUseCase: GetAllCharacterUsecase,
        getCharacterUseCase: GetSingleCharacterUsecase

    ): ViewModelFactory {
        return ViewModelFactory(getCharactersUseCase, getCharacterUseCase)
    }
}
