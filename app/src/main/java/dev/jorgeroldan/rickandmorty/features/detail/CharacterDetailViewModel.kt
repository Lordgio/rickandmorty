package dev.jorgeroldan.rickandmorty.features.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jorgeroldan.rickandmorty.data.CharacterRepository
import dev.jorgeroldan.rickandmorty.data.LocalStorage
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class CharacterDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val localStorage: LocalStorage,
    private val characterId: String,
) : ViewModel() {

    val state = savedStateHandle.getStateFlow<CharacterDetailState>(CHARACTER_DETAIL_STATE, CharacterDetailState.Idle)

    init {
        viewModelScope.launch {
            val savedId = savedStateHandle.get<String>(CHARACTER_DETAIL_ID)
            val currentState = savedStateHandle.get<CharacterDetailState>(CHARACTER_DETAIL_STATE)

            if (characterId == savedId && currentState is CharacterDetailState.Data) {
                // State is already saved. The stateFlow will restore itself
            } else {
                // Fetch new data
                savedStateHandle[CHARACTER_DETAIL_ID] = characterId
                savedStateHandle[CHARACTER_DETAIL_STATE] = CharacterDetailState.Loading
                fetchCharacterData(characterId)
            }
        }
    }

    private suspend fun fetchCharacterData(id: String) {
        repository.getCharacter(id).fold(
            ifLeft = { savedStateHandle[CHARACTER_DETAIL_STATE] = CharacterDetailState.Error },
            ifRight = {
                savedStateHandle[CHARACTER_DETAIL_STATE] = CharacterDetailState.Data(
                    it.toCharacterDetailInfo(localStorage.isFavourite(it.id ?: ""))
                )
            }
        )
    }

    fun onUiEvent(event: UiEvents) {
        if (event is UiEvents.OnFavouriteClicked) {
            handleFavourites()
        }
    }

    private fun handleFavourites() {
        val model = state.value
        if (model !is CharacterDetailState.Data) return

        if (model.character.isFavourite) {
            localStorage.deleteFavourite(model.character.id)
        } else {
            localStorage.storeFavourite(model.character.id)
        }

        savedStateHandle[CHARACTER_DETAIL_STATE] = CharacterDetailState.Data(
            model.character.copy(isFavourite = !model.character.isFavourite))
    }

    sealed interface CharacterDetailState {
        @Parcelize
        data object Idle : CharacterDetailState, Parcelable
        @Parcelize
        data object Loading : CharacterDetailState, Parcelable
        @Parcelize
        data class Data(val character: CharacterDetailInfo): CharacterDetailState, Parcelable
        @Parcelize
        data object Error : CharacterDetailState, Parcelable
    }

    sealed interface UiEvents {
        data object OnFavouriteClicked : UiEvents
    }

    companion object {
        private const val CHARACTER_DETAIL_STATE = "character_detail_state"
        private const val CHARACTER_DETAIL_ID = "character_detail_id"
    }
}
