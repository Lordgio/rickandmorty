package dev.jorgeroldan.rickandmorty.data

import android.content.SharedPreferences
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalStorageImplTest {

    private lateinit var storage: LocalStorage
    private val preferences: SharedPreferences = mockk(relaxed = true)

    @Before
    fun setup() {

        storage = LocalStorageImpl(preferences)
    }

    @Test
    fun `isFavourite with empty list loads it from prefs and check without match`() = runTest {
        every { preferences.getString(any(), any()) } returns ""

        val result = storage.isFavourite("test")

        verify { preferences.getString(any(), any()) }
        Assert.assertFalse(result)
    }

    @Test
    fun `isFavourite with empty list loads it from prefs and check with match`() = runTest {
        every { preferences.getString(any(), any()) } returns "1|2|test"

        val result = storage.isFavourite("test")

        verify { preferences.getString(any(), any()) }
        Assert.assertTrue(result)
    }

    @Test
    fun `storeFavourite with empty characterId does nothing`() = runTest {
        justRun { preferences.edit().putString(any(), any()).apply() }

        storage.storeFavourite("")

        verify(exactly = 0) { preferences.edit().putString(any(), any()).apply() }
    }

    @Test
    fun `storeFavourite with valid characterId does nothing`() = runTest {
        justRun { preferences.edit().putString(any(), any()).apply() }

        storage.storeFavourite("test")

        verify { preferences.edit().putString(any(), any()).apply() }
    }

    @Test
    fun `deleteFavourite with empty characterId does nothing`() = runTest {
        justRun { preferences.edit().putString(any(), any()).apply() }

        storage.deleteFavourite("")

        verify(exactly = 0) { preferences.edit().putString(any(), any()).apply() }
    }

    @Test
    fun `deleteFavourite with valid characterId does nothing`() = runTest {
        every { preferences.getString(any(), any()) } returns "1|2|test"
        justRun { preferences.edit().putString(any(), any()).apply() }
        storage.isFavourite("test")

        storage.deleteFavourite("test")

        verify { preferences.edit().putString(any(), any()).apply() }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}