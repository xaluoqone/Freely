package com.xaluoqone.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LocalThemeColors = compositionLocalOf {
    TealColorPalette
}

@Stable
object AppTheme {
    val colors: ThemeColors
        @Composable
        get() = LocalThemeColors.current

    sealed interface Theme {
        object White : Theme
        object Teal : Theme
        object Cyan : Theme
        object BlueGray : Theme
    }
}

@Stable
class ThemeColors(
    primary: Color,
    onPrimary: Color,
    primaryVariant: Color,
    onPrimaryVariant: Color,
    secondary: Color,
    onSecondary: Color,
    background: Color,
    onBackground: Color,
    systemBarsDarkContentEnabled: Boolean,
) {
    var primary by mutableStateOf(primary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var onPrimaryVariant by mutableStateOf(onPrimaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var background by mutableStateOf(background)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var systemBarsDarkContentEnabled by mutableStateOf(systemBarsDarkContentEnabled)
        private set
}

private val WhiteColorPalette = ThemeColors(
    primary = White,
    onPrimary = Black900,
    primaryVariant = White500,
    onPrimaryVariant = Black500,
    secondary = White,
    onSecondary = Black900,
    background = White,
    onBackground = Black900,
    systemBarsDarkContentEnabled = true,
)

private val TealColorPalette = ThemeColors(
    primary = Teal500,
    onPrimary = White,
    primaryVariant = Teal700,
    onPrimaryVariant = White,
    secondary = Teal200,
    onSecondary = White,
    background = White,
    onBackground = Black900,
    systemBarsDarkContentEnabled = false,
)

private val CyanColorPalette = ThemeColors(
    primary = Cyan500,
    onPrimary = White,
    primaryVariant = Cyan700,
    onPrimaryVariant = White,
    secondary = Cyan200,
    onSecondary = White,
    background = White,
    onBackground = Black900,
    systemBarsDarkContentEnabled = false,
)

private val BlueGrayColorPalette = ThemeColors(
    primary = BlueGray500,
    onPrimary = White,
    primaryVariant = BlueGray700,
    onPrimaryVariant = White,
    secondary = BlueGray200,
    onSecondary = White,
    background = White,
    onBackground = Black900,
    systemBarsDarkContentEnabled = false,
)

@Composable
fun AppTheme(theme: AppTheme.Theme, content: @Composable () -> Unit) {
    val targetTheme = when (theme) {
        AppTheme.Theme.White -> WhiteColorPalette
        AppTheme.Theme.BlueGray -> BlueGrayColorPalette
        AppTheme.Theme.Cyan -> CyanColorPalette
        AppTheme.Theme.Teal -> TealColorPalette
    }
    val primary = animateColorAsState(targetTheme.primary, TweenSpec(600))
    val onPrimary = animateColorAsState(targetTheme.onPrimary, TweenSpec(600))
    val primaryVariant = animateColorAsState(targetTheme.primaryVariant, TweenSpec(600))
    val onPrimaryVariant = animateColorAsState(targetTheme.onPrimaryVariant, TweenSpec(600))
    val secondary = animateColorAsState(targetTheme.secondary, TweenSpec(600))
    val onSecondary = animateColorAsState(targetTheme.onSecondary, TweenSpec(600))
    val background = animateColorAsState(targetTheme.background, TweenSpec(600))
    val onBackground = animateColorAsState(targetTheme.onBackground, TweenSpec(600))

    val colors = ThemeColors(
        primary = primary.value,
        onPrimary = onPrimary.value,
        primaryVariant = primaryVariant.value,
        onPrimaryVariant = onPrimaryVariant.value,
        secondary = secondary.value,
        onSecondary = onSecondary.value,
        background = background.value,
        onBackground = onBackground.value,
        systemBarsDarkContentEnabled = targetTheme.systemBarsDarkContentEnabled
    )

    CompositionLocalProvider(LocalThemeColors provides colors) {
        val systemUiController = rememberSystemUiController()
        val systemBarDarkContentEnabled = AppTheme.colors.systemBarsDarkContentEnabled
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent)
            systemUiController.systemBarsDarkContentEnabled = systemBarDarkContentEnabled
        }
        content()
    }
}