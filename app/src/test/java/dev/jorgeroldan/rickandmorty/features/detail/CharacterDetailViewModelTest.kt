package dev.jorgeroldan.rickandmorty.features.detail

import androidx.lifecycle.SavedStateHandle
import arrow.core.Either
import dev.jorgeroldan.rickandmorty.data.CharacterRepository
import dev.jorgeroldan.rickandmorty.data.LocalStorage
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterDetailViewModelTest {
    private lateinit var viewModel: CharacterDetailViewModel
    private val repository: CharacterRepository = mockk(relaxed = true)
    private val localStorage: LocalStorage = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `on init viewModel without stored state saves data and fetch character info`() = runTest {
        every { savedStateHandle.get<String>("character_detail_id") } returns ""
        every { savedStateHandle.get<CharacterDetailViewModel.CharacterDetailState>("character_detail_state") } returns CharacterDetailViewModel.CharacterDetailState.Idle
        coEvery { repository.getCharacter(any()) } returns Either.Left(Exception())
        viewModel = CharacterDetailViewModel(savedStateHandle, repository, localStorage, "test")

        verify { savedStateHandle.set("character_detail_id", "test") }
        verify { savedStateHandle.set("character_detail_state", CharacterDetailViewModel.CharacterDetailState.Loading) }
        coVerify { repository.getCharacter("test") }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}