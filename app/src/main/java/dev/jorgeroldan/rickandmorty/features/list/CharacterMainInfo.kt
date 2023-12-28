package dev.jorgeroldan.rickandmorty.features.list

import dev.jorgeroldan.rickandmorty.fragment.CharacterShortDetail

data class CharacterMainInfo(
    val id: String,
    val name: String,
    val image: String,
    val status: CharacterStatus,
    val gender: CharacterGender
)

enum class CharacterStatus { ALIVE, DEAD, UNKNOWN }

enum class CharacterGender { FEMALE, MALE, GENDERLESS, UNKNOWN }

fun CharacterShortDetail.toCharacterMainInfo() = CharacterMainInfo(
    id = this.id ?: "",
    name = this.name ?: "",
    image = this.image ?: "",
    status = (this.status ?: "").toCharacterStatus(),
    gender = (this.gender ?: "").toCharacterGender()
)

fun String.toCharacterStatus(): CharacterStatus {
    return when(this) {
        "Alive" -> CharacterStatus.ALIVE
        "Dead" -> CharacterStatus.DEAD
        else -> CharacterStatus.UNKNOWN
    }
}

fun String.toCharacterGender(): CharacterGender {
    return when(this) {
        "Female" -> CharacterGender.FEMALE
        "Male" -> CharacterGender.MALE
        "Genderless" -> CharacterGender.GENDERLESS
        else -> CharacterGender.UNKNOWN
    }
}

fun CharacterGender.toRequestString(): String {
    return when(this) {
        CharacterGender.FEMALE -> "Female"
        CharacterGender.MALE -> "Male"
        CharacterGender.GENDERLESS -> "Genderless"
        CharacterGender.UNKNOWN -> "Unknown"
    }
}
