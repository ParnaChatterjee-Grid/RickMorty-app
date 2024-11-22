package com.example.data.repository


import com.apollographql.apollo.ApolloClient
import com.example.data.GetAllCharactersQuery
import com.example.data.datamapper.getCharactersQueryToCharacterModel
import com.example.domain.models.Character
import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient):
    CharacterRepository {
    override suspend fun getCharacters(): List<Character?> {
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





