package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloGraphQLException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloWebSocketClosedException
import com.apollographql.apollo.exception.DefaultApolloException
import com.example.common.CustomExceptions
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
        } catch (ex: ApolloException) {
            when(ex){
                is ApolloNetworkException ->
                    throw CustomExceptions.ApolloNetworkError(ex)
                is ApolloGraphQLException ->
                    throw CustomExceptions.ApolloGraphQLError(ex)
                is ApolloHttpException ->
                    throw CustomExceptions.ApolloHttpError(ex)
                is ApolloWebSocketClosedException ->
                    throw CustomExceptions.ApolloWebSocketClosedError(ex)

                else -> {
                    // Default case for unknown ApolloExceptions
                    throw DefaultApolloException("Unknown Apollo error occurred",ex)
                }

            }

        }
    }
}

/*
               is com.apollographql.apollo.exception.ApolloGraphQLException -> TODO()
               is com.apollographql.apollo.exception.ApolloHttpException -> TODO()
            //   is com.apollographql.apollo.exception.ApolloNetworkException -> TODO()
               is com.apollographql.apollo.exception.ApolloParseException -> TODO()
               is com.apollographql.apollo.exception.ApolloWebSocketClosedException -> TODO()
               com.apollographql.apollo.exception.ApolloWebSocketForceCloseException -> TODO()
               is com.apollographql.apollo.exception.AutoPersistedQueriesNotSupported -> TODO()
               is com.apollographql.apollo.exception.CacheMissException -> TODO()
               is com.apollographql.apollo.exception.DefaultApolloException -> TODO()
               is com.apollographql.apollo.exception.HttpCacheMissException -> TODO()
               is com.apollographql.apollo.exception.JsonDataException -> TODO()
               is com.apollographql.apollo.exception.JsonEncodingException -> TODO()
               is com.apollographql.apollo.exception.MissingValueException -> TODO()
               is com.apollographql.apollo.exception.NoDataException -> TODO()
               is com.apollographql.apollo.exception.NullOrMissingField -> TODO()
               is com.apollographql.apollo.exception.RouterError -> TODO()
               is com.apollographql.apollo.exception.SubscriptionConnectionException -> TODO()
               is com.apollographql.apollo.exception.SubscriptionOperationException -> TODO()*/



