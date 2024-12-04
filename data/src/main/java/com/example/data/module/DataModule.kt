package com.example.data.module

import com.apollographql.apollo.ApolloClient
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharacterDetailsUsecase
import com.example.domain.usecases.GetCharactersUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    @Provides
    @Singleton
    fun provideCharacterRepository(apolloClient: ApolloClient): CharacterRepository {
        return CharacterRepositoryImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(repository: CharacterRepository): GetCharactersUsecase {
        return GetCharactersUsecase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCharacterDetailsUsecase(repository: CharacterRepository): GetCharacterDetailsUsecase{
        return GetCharacterDetailsUsecase(repository)
    }
}
