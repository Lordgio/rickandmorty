package dev.jorgeroldan.rickandmorty.ui.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview group for full screens, configured to show system ui in light and dark mode
 */
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
annotation class ScreenPreview()

/**
 * Preview group for ui components, configured to show ui in light and dark mode
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
annotation class ComponentPreview()
