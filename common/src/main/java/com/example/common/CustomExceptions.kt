package com.example.common

sealed class CustomExceptions(message: String) : Exception(message) {

 // Represents network-related errors
 class ApolloNetworkError(cause: Throwable? = null) : Exception(cause)
 // Represents Socket errors
 class ApolloWebSocketClosedError(cause: Throwable? = null) : Exception(cause)
 // Represents unexpected errors
 class ApolloGraphQLError(cause: Throwable? = null) : Exception(cause)
 // Represents invalid input errors
 class ApolloHttpError(cause: Throwable? = null) : Exception(cause)
}

