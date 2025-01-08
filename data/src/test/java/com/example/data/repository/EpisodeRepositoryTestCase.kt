package com.example.data.repository

import com.apollographql.apollo.ApolloClient
import com.example.data.GetEpisodeDetailsByIdQuery
import com.example.domain.models.Characters
import com.example.domain.models.Episode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class EpisodeRepositoryTestCase {
    private lateinit var mockServer: MockWebServer
    private lateinit var apolloClient: ApolloClient
    private lateinit var episodeRepository: EpisodeRepositoryImpl

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url("").toString()).build()
        episodeRepository = EpisodeRepositoryImpl(apolloClient)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }

    @Test
    fun `when EpisodeRepository receives response from mockserver then return success`() =
        runTest {
            val expectedEpisodes =
                Episode(
                    id = "2",
                    name = "Lawnmower Dog",
                    airdate = "December 9, 2013",
                    episode = "S01E02",
                    listOf(
                        Characters(
                            "1", "Rick Sanchez",
                            "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                        ),
                        Characters(
                            "2", "Morty Smith",
                            "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
                        )
                    )
                )

            //Given
            val mockResponse = """
        {
            "data": {
               "episode": 
               {
                "id": "2",
                "name": "Lawnmower Dog",
                "air_date": "December 9, 2013",
                "episode": "S01E02", 
                "characters": [
                  {
                    "id": "1",
                    "name": "Rick Sanchez",
                    "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                  },
                  {
                     "id": "2",
                      "name": "Morty Smith",
                      "image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
                  }
               ]
              }     
            } 
        }
        """.trimIndent()
            mockServer.enqueue(
                okhttp3.mockwebserver.MockResponse().setResponseCode(200)
                    .setBody(mockResponse)
            )

            //When
            val episodes = episodeRepository.getEpisodeById("2")
            //Then
            assertEquals(expectedEpisodes, episodes)
        }

    @Test
    fun `when throws exception from apolloClient then EpisodeRepository receives it`() =
        runTest {

            val apolloClientServer = mockk<ApolloClient>(relaxed = true)
            val episodeRepository = EpisodeRepositoryImpl(apolloClientServer)
            //Given
            val exception = RuntimeException("Error fetching while episodes details")
            coEvery {
                apolloClientServer.query(GetEpisodeDetailsByIdQuery("2")).execute()
            } throws exception
            //When
            try {
                episodeRepository.getEpisodeById("2")
            } catch (actualException: RuntimeException) {
                //Then
                assertEquals(exception, actualException)
            }
        }

}