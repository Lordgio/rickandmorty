package dev.jorgeroldan.rickandmorty.features.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.jorgeroldan.rickandmorty.data.CharacterRepository
import dev.jorgeroldan.rickandmorty.data.PagedCharacterSource
import dev.jorgeroldan.rickandmorty.fragment.CharacterShortDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class CharacterListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val characterSource = savedStateHandle.getStateFlow<CharacterGender?>(SOURCE_KEY, null)
        .flatMapLatest { filter ->
            Pager(
                config = PagingConfig(pageSize = 20),
                initialKey = 1,
                pagingSourceFactory = { PagedCharacterSource(repository, filter) }
            ).flow.cachedIn(viewModelScope)
        }

    fun onUiEvent(event: UiEvents) {
        if (event is UiEvents.UpdateFilter) {
            savedStateHandle[SOURCE_KEY] = event.filter
        }
    }

    sealed interface UiEvents {
        data class UpdateFilter(val filter: CharacterGender?) : UiEvents
    }

    companion object {
        private const val SOURCE_KEY = "character_source_key"
    }
}
