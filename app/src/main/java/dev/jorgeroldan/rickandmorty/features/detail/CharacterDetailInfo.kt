package dev.jorgeroldan.rickandmorty.features.detail

import android.os.Parcelable
import dev.jorgeroldan.rickandmorty.features.list.CharacterGender
import dev.jorgeroldan.rickandmorty.features.list.CharacterStatus
import dev.jorgeroldan.rickandmorty.features.list.toCharacterGender
import dev.jorgeroldan.rickandmorty.features.list.toCharacterStatus
import dev.jorgeroldan.rickandmorty.fragment.CharacterDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailInfo(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val status: CharacterStatus = CharacterStatus.UNKNOWN,
    val species: String = "",
    val type: String = "",
    val gender: CharacterGender = CharacterGender.UNKNOWN,
    val location: String = "",
    val origin: String = "",
    val isFavourite: Boolean = false
) : Parcelable

fun CharacterDetail.toCharacterDetailInfo(isFavourite: Boolean) = CharacterDetailInfo(
    id = this.id ?: "",
    name = this.name ?: "",
    image = this.image ?: "",
    status = (this.status ?: "").toCharacterStatus(),
    species = this.species ?: "",
    type = this.type ?: "",
    gender = (this.gender ?: "").toCharacterGender(),
    location = this.location?.name ?: "",
    origin = this.origin?.name ?: "",
    isFavourite = isFavourite
)
