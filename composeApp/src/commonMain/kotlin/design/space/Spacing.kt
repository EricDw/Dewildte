package design.space

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class Spacing(
    val baseSpacing: Float = 8F,
    val spacing0: Float = 0F * baseSpacing,
    val spacing025: Float = 0.25F * baseSpacing,
    val spacing050: Float = 0.50F * baseSpacing,
    val spacing075: Float = 0.75F * baseSpacing,
    val spacing100: Float = 1F * baseSpacing,
    val spacing150: Float = 1.5F * baseSpacing,
    val spacing200: Float = 2F * baseSpacing,
)

internal val LocalSpacing = staticCompositionLocalOf {
    Spacing()
}

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current