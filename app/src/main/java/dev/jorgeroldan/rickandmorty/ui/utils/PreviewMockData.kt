package dev.jorgeroldan.rickandmorty.ui.utils

import dev.jorgeroldan.rickandmorty.features.detail.CharacterDetailInfo
import dev.jorgeroldan.rickandmorty.features.list.CharacterGender
import dev.jorgeroldan.rickandmorty.features.list.CharacterMainInfo
import dev.jorgeroldan.rickandmorty.features.list.CharacterStatus

object PreviewMockData {

    val characterList: List<CharacterMainInfo> = listOf(
        CharacterMainInfo("1", "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", CharacterStatus.ALIVE, CharacterGender.MALE),
        CharacterMainInfo("2", "Morty Smith", "https://rickandmortyapi.com/api/character/avatar/2.jpeg", CharacterStatus.ALIVE, CharacterGender.MALE),
        CharacterMainInfo("3", "Summer Smith", "https://rickandmortyapi.com/api/character/avatar/3.jpeg", CharacterStatus.ALIVE, CharacterGender.FEMALE),
        CharacterMainInfo("4", "Beth Smith", "https://rickandmortyapi.com/api/character/avatar/4.jpeg", CharacterStatus.ALIVE, CharacterGender.FEMALE),
        CharacterMainInfo("5", "Jerry Smith", "https://rickandmortyapi.com/api/character/avatar/5.jpeg", CharacterStatus.ALIVE, CharacterGender.MALE),
        CharacterMainInfo("6", "Abadango Cluster Princess", "https://rickandmortyapi.com/api/character/avatar/6.jpeg", CharacterStatus.ALIVE, CharacterGender.FEMALE),
        CharacterMainInfo("7", "Abradolf Lincler", "https://rickandmortyapi.com/api/character/avatar/7.jpeg", CharacterStatus.UNKNOWN, CharacterGender.MALE),
        CharacterMainInfo("8", "Adjudicator Rick", "https://rickandmortyapi.com/api/character/avatar/8.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("9", "Agency Director", "https://rickandmortyapi.com/api/character/avatar/9.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("10", "Alan Rails", "https://rickandmortyapi.com/api/character/avatar/10.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("11", "Albert Einstein", "https://rickandmortyapi.com/api/character/avatar/11.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("12", "Alexander", "https://rickandmortyapi.com/api/character/avatar/12.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("13", "Alien Googah", "https://rickandmortyapi.com/api/character/avatar/13.jpeg", CharacterStatus.UNKNOWN, CharacterGender.UNKNOWN),
        CharacterMainInfo("14", "Alien Morty", "https://rickandmortyapi.com/api/character/avatar/14.jpeg", CharacterStatus.UNKNOWN, CharacterGender.MALE),
        CharacterMainInfo("15", "Alien Rick", "https://rickandmortyapi.com/api/character/avatar/15.jpeg", CharacterStatus.UNKNOWN, CharacterGender.MALE),
        CharacterMainInfo("16", "Amish Cyborg", "https://rickandmortyapi.com/api/character/avatar/16.jpeg", CharacterStatus.DEAD, CharacterGender.MALE),
        CharacterMainInfo("17", "Annie", "https://rickandmortyapi.com/api/character/avatar/17.jpeg", CharacterStatus.ALIVE, CharacterGender.FEMALE),
        CharacterMainInfo("18", "Antenna Morty", "https://rickandmortyapi.com/api/character/avatar/18.jpeg", CharacterStatus.ALIVE, CharacterGender.MALE),
        CharacterMainInfo("19", "Antenna Rick", "https://rickandmortyapi.com/api/character/avatar/19.jpeg", CharacterStatus.UNKNOWN, CharacterGender.MALE),
        CharacterMainInfo("20", "Ants in my Eyes Johnson", "https://rickandmortyapi.com/api/character/avatar/20.jpeg", CharacterStatus.UNKNOWN, CharacterGender.MALE),
    )

    val detailCharacter = CharacterDetailInfo(
        id = "1",
        name = "Rick Sanchez",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        status = CharacterStatus.ALIVE,
        species = "Human",
        type = "",
        gender = CharacterGender.MALE,
        location = "Citadel of Ricks",
        origin = "Earth (C-137)",
        isFavourite = false
    )
}
