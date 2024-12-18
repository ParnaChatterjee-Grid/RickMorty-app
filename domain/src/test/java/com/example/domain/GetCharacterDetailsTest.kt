package com.example.domain


import com.example.domain.models.CharacterDetails
import com.example.domain.models.Episode
import com.example.domain.models.Locations
import com.example.domain.models.Origins
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharacterDetailsUsecase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
class GetCharacterDetailsTest {
    private lateinit var getCharacterDetails : GetCharacterDetailsUsecase
    private val repository = mockk<CharacterRepository>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setup(){
        getCharacterDetails = GetCharacterDetailsUsecase(repository)
    }

    @Test
    fun `should return details of character when repository returns data`() = runTest(testDispatcher) {
        val expectedResult = CharacterDetails(
            id = "1",
            name = "Rick",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            status = "Alive",
            species = "Human",
            gender = "male",
            origin = Origins(name = "Earth", dimension = "Replacement Dimension"),
            locations = Locations(id = "1", name = "Earth", dimension = "Dimension"),
            episodes = listOf(
                Episode("6", "Rick Potion #9", "January 27, 2014","Episode1"),
                Episode("7", "Raising Gazorpazorp", "March 10, 2014","Episode2")
            )
        )

        coEvery { repository.getCharacterById("1") } returns expectedResult
        val testCaseCharacters = getCharacterDetails.invoke("1")
        assertEquals(expectedResult,testCaseCharacters)
    }

    @Test
    fun ` for runtime exception to show error`() = runTest(testDispatcher) {
        val exception = RuntimeException("Error fetching character results")
        coEvery { repository.getCharacterById("1") } throws exception
        try {
            val result = getCharacterDetails.invoke("1")
        }
        catch( e :RuntimeException)  {
            assertEquals("java.lang.RuntimeException: Error fetching character results", ""+e)
        }
    }

    @Test
    fun `should return empty character details when repository returns no data`() = runTest {
         val emptyCharacterDetails = null
        // Mocking the suspending function using coEvery
        coEvery { repository.getCharacterById("1") } returns emptyCharacterDetails
        val result = getCharacterDetails.invoke("1")
        assertEquals(result,emptyCharacterDetails)
    }
}
