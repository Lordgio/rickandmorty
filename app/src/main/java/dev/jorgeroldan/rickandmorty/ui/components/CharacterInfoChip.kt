package dev.jorgeroldan.rickandmorty.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jorgeroldan.rickandmorty.R
import dev.jorgeroldan.rickandmorty.features.list.CharacterGender
import dev.jorgeroldan.rickandmorty.features.list.CharacterStatus

@Composable
fun CharacterStatusChip(status: CharacterStatus, modifier: Modifier = Modifier) {
    val statusText = when (status) {
        CharacterStatus.ALIVE -> stringResource(id = R.string.character_status_alive)
        CharacterStatus.DEAD -> stringResource(id = R.string.character_status_dead)
        CharacterStatus.UNKNOWN -> stringResource(id = R.string.character_status_unknown)
    }
    val statusIcon = when (status) {
        CharacterStatus.ALIVE -> painterResource(id = R.drawable.status_alive)
        CharacterStatus.DEAD -> painterResource(id = R.drawable.status_dead)
        CharacterStatus.UNKNOWN -> painterResource(id = R.drawable.unknown)
    }
    CharacterInfoChip(text = statusText, iconPainter = statusIcon, modifier = modifier)
}

@Composable
fun CharacterGenderChip(gender: CharacterGender, modifier: Modifier = Modifier) {
    val genderText = when (gender) {
        CharacterGender.FEMALE -> stringResource(id = R.string.character_gender_female)
        CharacterGender.MALE -> stringResource(id = R.string.character_gender_male)
        CharacterGender.GENDERLESS -> stringResource(id = R.string.character_gender_genderless)
        CharacterGender.UNKNOWN -> stringResource(id = R.string.character_gender_unknown)
    }
    val genderIcon = when (gender) {
        CharacterGender.FEMALE -> painterResource(id = R.drawable.gender_female)
        CharacterGender.MALE -> painterResource(id = R.drawable.gender_male)
        else -> painterResource(id = R.drawable.unknown)
    }
    CharacterInfoChip(text = genderText, iconPainter = genderIcon, modifier = modifier)
}

@Composable
private fun CharacterInfoChip(
    text: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        modifier = modifier,
        onClick = {},
        label = { Text(text = text) },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = iconPainter,
                contentDescription = text)
        },
        enabled = false,
        colors = AssistChipDefaults.assistChipColors(
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconContentColor = MaterialTheme.colorScheme.primary,
        ),
        border = AssistChipDefaults.assistChipBorder(
            disabledBorderColor = MaterialTheme.colorScheme.outline,
        )
    )
}
