package dev.jorgeroldan.rickandmorty.di

import android.content.Context
import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import dev.jorgeroldan.rickandmorty.data.CharacterRepository
import dev.jorgeroldan.rickandmorty.data.LocalStorage
import dev.jorgeroldan.rickandmorty.data.LocalStorageImpl
import dev.jorgeroldan.rickandmorty.features.detail.CharacterDetailViewModel
import dev.jorgeroldan.rickandmorty.features.list.CharacterListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    fun getModule() = module {
        single<SharedPreferences> { androidContext().getSharedPreferences("RickAndMorty", Context.MODE_PRIVATE) }
        single<ApolloClient> { buildApolloClient() }
        single<CharacterRepository> { CharacterRepository(get()) }
        single<LocalStorage> { LocalStorageImpl(get()) }
        viewModel<CharacterListViewModel> { CharacterListViewModel(get(), get()) }
        viewModel<CharacterDetailViewModel> { CharacterDetailViewModel(get(), get(), get(), get()) }
    }

    private fun buildApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()
    }
}
