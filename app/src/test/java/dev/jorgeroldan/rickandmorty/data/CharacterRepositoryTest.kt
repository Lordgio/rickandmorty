package dev.jorgeroldan.rickandmorty.data

import arrow.core.Either
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.DefaultApolloException
import dev.jorgeroldan.rickandmorty.GetCharacterByIdQuery
import dev.jorgeroldan.rickandmorty.GetCharactersQuery
import dev.jorgeroldan.rickandmorty.fragment.CharacterDetail
import dev.jorgeroldan.rickandmorty.type.FilterCharacter
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CharacterRepositoryTest {

    private lateinit var repository: CharacterRepository
    private val client: ApolloClient = mockk(relaxed = true)

    @Before
    fun setup() {

        repository = CharacterRepository(client)
    }

    @Test
    fun `getCharacter with error response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharacterByIdQuery.Data> = ApolloResponse
            .Builder(GetCharacterByIdQuery(""), UUID.randomUUID())
            .errors(listOf(Error.Builder("").build()))
            .build()
        coEvery { client.query(any<GetCharacterByIdQuery>()).execute() } returns testResponse

        val result = repository.getCharacter("")

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacter with exception response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharacterByIdQuery.Data> = ApolloResponse
            .Builder(GetCharacterByIdQuery(""), UUID.randomUUID())
            .errors(null)
            .exception(DefaultApolloException())
            .build()
        coEvery { client.query(any<GetCharacterByIdQuery>()).execute() } returns testResponse

        val result = repository.getCharacter("")

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacter with null data response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharacterByIdQuery.Data> = ApolloResponse
            .Builder(GetCharacterByIdQuery(""), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(null)
            .build()
        coEvery { client.query(any<GetCharacterByIdQuery>()).execute() } returns testResponse

        val result = repository.getCharacter("")

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacter with null character response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharacterByIdQuery.Data> = ApolloResponse
            .Builder(GetCharacterByIdQuery(""), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(GetCharacterByIdQuery.Data(null))
            .build()
        coEvery { client.query(any<GetCharacterByIdQuery>()).execute() } returns testResponse

        val result = repository.getCharacter("")

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacter with valid character response returns right`() = runTest {
        val testResponse: ApolloResponse<GetCharacterByIdQuery.Data> = ApolloResponse
            .Builder(GetCharacterByIdQuery(""), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(GetCharacterByIdQuery.Data(GetCharacterByIdQuery.Character("", CharacterDetail(null, null, null, null, null, null, null, null, null,))))
            .build()
        coEvery { client.query(any<GetCharacterByIdQuery>()).execute() } returns testResponse

        val result = repository.getCharacter("")

        Assert.assertTrue(result is Either.Right)
    }

    @Test
    fun `getCharacters with error response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharactersQuery.Data> = ApolloResponse
            .Builder(GetCharactersQuery(0, FilterCharacter()), UUID.randomUUID())
            .errors(listOf(Error.Builder("").build()))
            .build()
        coEvery { client.query(any<GetCharactersQuery>()).execute() } returns testResponse

        val result = repository.getCharacters(0, null)

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacters with exception response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharactersQuery.Data> = ApolloResponse
            .Builder(GetCharactersQuery(0, FilterCharacter()), UUID.randomUUID())
            .errors(null)
            .exception(DefaultApolloException())
            .build()
        coEvery { client.query(any<GetCharactersQuery>()).execute() } returns testResponse

        val result = repository.getCharacters(0, null)

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacters with null data response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharactersQuery.Data> = ApolloResponse
            .Builder(GetCharactersQuery(0, FilterCharacter()), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(null)
            .build()
        coEvery { client.query(any<GetCharactersQuery>()).execute() } returns testResponse

        val result = repository.getCharacters(0,  null)

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacters with null character response returns left`() = runTest {
        val testResponse: ApolloResponse<GetCharactersQuery.Data> = ApolloResponse
            .Builder(GetCharactersQuery(0, FilterCharacter()), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(GetCharactersQuery.Data(null))
            .build()
        coEvery { client.query(any<GetCharactersQuery>()).execute() } returns testResponse

        val result = repository.getCharacters(0, null)

        Assert.assertTrue(result is Either.Left)
    }

    @Test
    fun `getCharacters with valid character response returns right`() = runTest {
        val testResponse: ApolloResponse<GetCharactersQuery.Data> = ApolloResponse
            .Builder(GetCharactersQuery(0, FilterCharacter()), UUID.randomUUID())
            .errors(null)
            .exception(null)
            .data(GetCharactersQuery.Data(GetCharactersQuery.Characters(
                info = GetCharactersQuery.Info(null, null, null, null),
                results = listOf())))
            .build()
        coEvery { client.query(any<GetCharactersQuery>()).execute() } returns testResponse

        val result = repository.getCharacters(0, null)

        Assert.assertTrue(result is Either.Right)
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }
}