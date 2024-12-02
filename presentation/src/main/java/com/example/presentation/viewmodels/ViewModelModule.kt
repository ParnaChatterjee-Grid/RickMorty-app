package com.example.presentation.viewmodels

import com.example.domain.usecases.GetCharactersUsecase
import com.example.presentation.viewmodels.ViewModelfactory
//import com.example.presentation.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule{
    @Provides
    @Singleton

    fun provideViewModelFactory(getCharactersUsecase : GetCharactersUsecase): ViewModelfactory {
        return ViewModelfactory(getCharactersUsecase)
    }
}
