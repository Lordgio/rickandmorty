package dev.jorgeroldan.rickandmorty.features.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.jorgeroldan.rickandmorty.R
import dev.jorgeroldan.rickandmorty.ui.components.CharacterGenderChip
import dev.jorgeroldan.rickandmorty.ui.components.CharacterStatusChip
import dev.jorgeroldan.rickandmorty.ui.components.FullScreenLoader
import dev.jorgeroldan.rickandmorty.ui.components.GenericErrorScreen
import dev.jorgeroldan.rickandmorty.ui.theme.RickAndMortyTheme
import dev.jorgeroldan.rickandmorty.ui.utils.PreviewMockData
import dev.jorgeroldan.rickandmorty.ui.utils.ScreenPreview
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CharacterDetailScreen(
    characterId: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val viewModel: CharacterDetailViewModel = koinViewModel { parametersOf(characterId) }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val value = state.value) {
        is CharacterDetailViewModel.CharacterDetailState.Data -> {
            CharacterDetailScreenContent(
                character = value.character,
                onBack = onBack,
                onFavourite = { viewModel.onUiEvent(CharacterDetailViewModel.UiEvents.OnFavouriteClicked) },
                modifier = modifier
            )
        }
        CharacterDetailViewModel.CharacterDetailState.Error -> GenericErrorScreen(onBackClick = onBack)
        CharacterDetailViewModel.CharacterDetailState.Idle -> { /* no-op */ }
        CharacterDetailViewModel.CharacterDetailState.Loading -> FullScreenLoader()
    }
}

@Composable
private fun CharacterDetailScreenContent(
    character: CharacterDetailInfo,
    onBack: () -> Unit,
    onFavourite: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {

        CharacterDetailHeader(
            character = character,
            onBack = onBack,
            onFavourite = onFavourite)
        
        CharacterDetailContent(character = character)
    }
}

@Composable
private fun CharacterDetailHeader(
    character: CharacterDetailInfo,
    onBack: () -> Unit,
    onFavourite: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.fillMaxWidth()
        )

        FilledTonalIconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
            )
        }

        FilledTonalIconButton(
            onClick = onFavourite,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (character.isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun CharacterDetailContent(character: CharacterDetailInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CharacterStatusChip(status = character.status)
            CharacterGenderChip(gender = character.gender)
        }
        OriginLocationInfoCard(origin = character.origin, location = character.location)

    }
}

@Composable
private fun OriginLocationInfoCard(
    origin: String,
    location: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.5F)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.character_detail_origin),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(6.dp))
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = origin,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.character_detail_location),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(6.dp))
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = location,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@ScreenPreview
@Composable
private fun CharacterDetailScreenContentPreview() {
    RickAndMortyTheme {
        CharacterDetailScreenContent(
            character = PreviewMockData.detailCharacter,
            onBack = {},
            onFavourite = {}
        )
    }
}
