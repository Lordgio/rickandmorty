package dev.jorgeroldan.rickandmorty.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import dev.jorgeroldan.rickandmorty.R

@Composable
fun FullScreenLoader(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        val infiniteTransition = rememberInfiniteTransition(label = "LoaderAnimation")

        val rotation by infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = 360F,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing,)
            ),
            label = "RotationAnimation"
        )

        Image(
            painter = painterResource(id = R.drawable.loader_icon),
            contentDescription = "Loading",
            modifier = Modifier.rotate(rotation))
    }
}
