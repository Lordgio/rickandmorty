package dev.jorgeroldan.rickandmorty.features.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import dev.jorgeroldan.rickandmorty.R
import dev.jorgeroldan.rickandmorty.ui.components.CharacterGenderChip
import dev.jorgeroldan.rickandmorty.ui.components.CharacterStatusChip
import dev.jorgeroldan.rickandmorty.ui.components.FullScreenLoader
import dev.jorgeroldan.rickandmorty.ui.theme.RickAndMortyTheme
import dev.jorgeroldan.rickandmorty.ui.utils.ComponentPreview
import dev.jorgeroldan.rickandmorty.ui.utils.PreviewMockData
import dev.jorgeroldan.rickandmorty.ui.utils.ScreenPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    onCharacterClicked: (String) -> Unit,
) {
    val viewModel: CharacterListViewModel = koinViewModel()
    var currentFilter by remember { mutableStateOf<CharacterGender?>(null) }
    
    Column(modifier = modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        Image(
            painter = painterResource(id = R.drawable.rick_and_morty),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        CharacterFilterRow(
            currentFilter = currentFilter,
            onFilterSelected = { filter ->
                currentFilter = filter
                viewModel.onUiEvent(CharacterListViewModel.UiEvents.UpdateFilter(filter))
            }
        )
        CharacterList(characters = viewModel.characterSource, onCharacterClicked)
    }
}

@Composable
private fun CharacterList(
    characters: Flow<PagingData<CharacterMainInfo>>,
    onCharacterClicked: (String) -> Unit,
    modifier: Modifier = Modifier,) {

    val characterItems = characters.collectAsLazyPagingItems()


    AnimatedContent(targetState = characterItems.loadState.refresh, label = "") { loadState ->
        if (loadState == LoadState.Loading) {
            // Loading whole list, show full screen loading
            FullScreenLoader()
        } else {
            LazyColumn(modifier = modifier) {
                items(
                    count = characterItems.itemCount,
                    key = { index -> characterItems[index]?.id ?: "" },
                    contentType = { CharacterMainInfo::class }
                ) { index ->
                    val character = characterItems[index]
                    CharacterItem(character = character!!, onCharacterClicked = onCharacterClicked)
                }
                if (characterItems.loadState.append == LoadState.Loading) {
                    // Loading next page, show loading on context
                    item { ListLocalLoading() }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterItem(
    character: CharacterMainInfo,
    onCharacterClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onCharacterClicked.invoke(character.id) }
    ) {
        Row {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .widthIn(max = 100.dp)
                    .heightIn(max = 100.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Row {
                    CharacterStatusChip(status = character.status)
                    Spacer(modifier = Modifier.width(8.dp))
                    CharacterGenderChip(gender = character.gender)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CharacterFilterRow(
    currentFilter: CharacterGender? = null,
    onFilterSelected: (CharacterGender) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf(CharacterGender.FEMALE, CharacterGender.MALE, CharacterGender.GENDERLESS, CharacterGender.UNKNOWN)
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        filters.forEach { item ->
            FilterChip(
                selected = item == currentFilter,
                onClick = { onFilterSelected.invoke(item) },
                label = { Text(text = item.toRequestString()) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Composable
private fun ListLocalLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@ScreenPreview
@Composable
private fun CharacterListScreenPreview() {
    RickAndMortyTheme {
        CharacterList(
            characters = flowOf(PagingData.from(PreviewMockData.characterList)),
            onCharacterClicked = {})
    }
}

@ComponentPreview
@Composable
private fun CharacterItemPreview() {
    RickAndMortyTheme {
        CharacterItem(
            character = PreviewMockData.characterList.first(),
            onCharacterClicked = {}
        )
    }
}
