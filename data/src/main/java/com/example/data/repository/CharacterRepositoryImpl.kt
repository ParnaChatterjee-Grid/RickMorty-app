package com.example.data.repository


import com.apollographql.apollo.ApolloClient
import com.example.domain.models.CharacterSchema
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
    override suspend fun getCharacters(): Flow<Results<List<CharacterSchema>>> =

        flow{
            emit(Loading)
            val response = apolloClient.query(com.example.data.GetAllCharactersQuery()).execute()
            if (response.hasErrors()) {
                emit(Error(Exception("GraphQL errors: ${response.errors}")))
                return@flow // Exit the flow
            }
            val characters = response.data?.characters?.results?.map { character ->
                CharacterSchema(
                    id = character?.id.orEmpty(),
                    name = character?.name.orEmpty(),
                    image = character?.image.orEmpty(),
                )
            }?: emptyList()
            emit(Results.Success(characters))
        }.catch { e ->
            emit(Results.Error(e))
        }

    }






