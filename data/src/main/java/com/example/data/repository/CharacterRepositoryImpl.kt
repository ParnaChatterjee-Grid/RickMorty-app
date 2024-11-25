package com.example.data.repository

/*
import com.apollographql.apollo.ApolloClient
import com.example.data.datamapper.getCharactersQueryToCharacterModel
import com.example.domain.models.Character
import com.example.domain.repository.CharacterRepository
import com.example.domain.utils.Results
import com.example.domain.utils.Results.Error
import com.example.domain.utils.Results.Loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient):
    CharacterRepository{
    override suspend fun getCharacters(): Flow<Results<List<Character?>>> =
        flow{
            emit(Loading)
            val response = apolloClient.query(com.example.data.GetAllCharactersQuery()).execute()
            if (response.hasErrors()) {
                emit(Error(Exception("GraphQL errors: ${response.errors}")))
            }
            else {
                val characters = response.data?.characters?.results?.map {
                    getCharactersQueryToCharacterModel(it)
                } ?: emptyList()
                emit(Results.Success(characters))
            }
        }.catch { e ->
            emit(Error(e))
        }

    }*/


import com.apollographql.apollo.ApolloClient
import com.example.data.GetAllCharactersQuery
import com.example.data.datamapper.getCharactersQueryToCharacterModel
import com.example.domain.models.Characters
import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient):
    CharacterRepository {
    override suspend fun getCharacters(): List<Characters?> {
        return try {
            val response = apolloClient.query(GetAllCharactersQuery()).execute()
            val results = response.data?.characters?.results?.map {
                getCharactersQueryToCharacterModel(it)
            } ?: emptyList()
            results
        } catch (ex: Exception) {
            emptyList()
        }
    }
}






