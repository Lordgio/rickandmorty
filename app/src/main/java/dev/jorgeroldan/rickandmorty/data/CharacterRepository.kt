package dev.jorgeroldan.rickandmorty.data

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import dev.jorgeroldan.rickandmorty.GetCharacterByIdQuery
import dev.jorgeroldan.rickandmorty.GetCharactersQuery
import dev.jorgeroldan.rickandmorty.features.list.CharacterGender
import dev.jorgeroldan.rickandmorty.features.list.toRequestString
import dev.jorgeroldan.rickandmorty.fragment.CharacterDetail
import dev.jorgeroldan.rickandmorty.fragment.CharacterShortDetail
import dev.jorgeroldan.rickandmorty.type.FilterCharacter

class CharacterRepository(private val client: ApolloClient) {

    suspend fun getCharacter(characterId: String) : Either<Throwable, CharacterDetail> {
        return either {
            val response = client.query(GetCharacterByIdQuery(characterId)).execute()
            val model = response.data?.character
            ensure(!response.hasErrors()) { IllegalStateException() }
            ensure(response.exception == null) { IllegalStateException() }
            ensureNotNull(model) { IllegalStateException() }

            model.characterDetail
        }
    }

    suspend fun getCharacters(
        page: Int,
        gender: CharacterGender? = null
    ) : Either<Throwable, Pair<GetCharactersQuery.Info, List<CharacterShortDetail>>> {
        return either {
            val genderFilter = if (gender != null) Optional.present(gender.toRequestString()) else Optional.absent()
            val filter = FilterCharacter(gender = genderFilter)
            val query = GetCharactersQuery(page, filter)

            val response = client.query(query).execute()
            ensure(!response.hasErrors()) { IllegalStateException() }
            ensure(response.exception == null) { IllegalStateException() }

            val model = response.data?.characters
            ensureNotNull(model) { IllegalStateException() }

            val pagingInfo = model.info
            val items = model.results
            ensureNotNull(pagingInfo) { IllegalStateException() }
            ensureNotNull(items) { IllegalStateException() }

            pagingInfo to model.results.filterNotNull().map { it.characterShortDetail }
        }
    }
}