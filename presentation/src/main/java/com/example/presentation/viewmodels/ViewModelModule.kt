package com.example.presentation.viewmodels

import com.example.data.di.IoDispatcher
import com.example.domain.usecases.GetCharactersUsecase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class ViewModelModule{
    @Provides
    @Singleton
    fun provideViewModelFactory(getCharactersUsecase : GetCharactersUsecase,
                                @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): ViewModelfactory {
        return ViewModelfactory(getCharactersUsecase,coroutineDispatcher)
    }
}
