package com.example.presentation

import app.cash.turbine.test
import com.example.common.ResultState
import com.example.domain.models.Characters
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharactersUsecase
import com.example.presentation.viewmodels.CharacterViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {
    private lateinit var SUT: CharacterViewModel
    private  lateinit var charactersUseCase: GetCharactersUsecase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        charactersUseCase = mockk<GetCharactersUsecase>(relaxed = true)
        SUT = CharacterViewModel(charactersUseCase,testDispatcher)
    }
    @Test
    fun `should return charactersState is in loading state when loading characters`() =
        runTest(testDispatcher){
           val currentState =  SUT.charactersState.value
            assertEquals(ResultState.Loading,currentState)

    }

    @Test
    fun `get list of characters on success`() = runTest(testDispatcher) {
        val characters = getCharacters()
        coEvery { charactersUseCase.invoke() } returns characters
        SUT.getAllCharacters()
        val currentState =  SUT.charactersState.value
        assertEquals(ResultState.Loading,currentState)
        SUT.charactersState.test {
            assertTrue(awaitItem() is ResultState)
            val successState = awaitItem()
            assertThat(successState).isInstanceOf(ResultState.Success::class.java)
            val actualCharacters = flowOf((successState as ResultState.Success).data).toList()
            assertThat(actualCharacters.get(0).size).isEqualTo(characters.size)
            cancelAndConsumeRemainingEvents()
        }
    }
    @Test
    fun `while fetching characters list test for runtime exception to show error`() = runTest(testDispatcher) {
        val exception = RuntimeException("Error fetching character results")
        val repository = mockk<CharacterRepository>(relaxed = true)
        coEvery { repository.getCharacters() } throws exception
        try {
            SUT.getAllCharacters()
        }
        catch (e :RuntimeException){
            assertEquals("Error fetching character results", ""+e)
        }
    }
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    fun getCharacters():List<Characters>{
        val CharactersList = listOf(Characters("101","aaa","https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
            Characters("202","bbb","https://rickandmortyapi.com/api/character/avatar/2.jpeg"),
            Characters("303","ccc","https://rickandmortyapi.com/api/character/avatar/3.jpeg"))
        return CharactersList
    }
}
