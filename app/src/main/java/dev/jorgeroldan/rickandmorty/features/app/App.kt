package dev.jorgeroldan.rickandmorty.features.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import dev.jorgeroldan.rickandmorty.features.detail.CharacterDetailScreen
import dev.jorgeroldan.rickandmorty.features.list.CharacterListScreen
import dev.jorgeroldan.rickandmorty.ui.theme.RickAndMortyTheme

sealed class Screens(val route: String) {
    data object CharacterListScreen : Screens("CharacterList")
    data object CharacterDetailScreen : Screens("CharacterDetail")
}

@Composable
fun App() {

    RickAndMortyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screens.CharacterListScreen.route) {

                composable(Screens.CharacterListScreen.route) {
                    CharacterListScreen { characterId ->
                        navController.navigate(Screens.CharacterDetailScreen.route + "/${characterId}")
                    }
                }
                composable(
                    route = Screens.CharacterDetailScreen.route + "/{id}",
                    deepLinks = listOf(navDeepLink { uriPattern = "https://alkimiirickandmorty.com/details/{id}" })
                ) { backStackEntry ->
                    CharacterDetailScreen(
                        characterId = backStackEntry.arguments?.getString("id") as String,
                        onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
