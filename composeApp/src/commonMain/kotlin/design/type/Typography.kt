package design.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dewildte.composeapp.generated.resources.*
import org.jetbrains.compose.resources.Font

/**
 * Compose typography interface for the JetBrains Mono Fonts.
 */
val jetBrainsMono: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.JetBrainsMono_Bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_BoldItalic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_ExtraBold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_ExtraBoldItalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_ExtraLight, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_ExtraLightItalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_Italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_Light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_LightItalic, weight = FontWeight.Light, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_Medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_MediumItalic, weight = FontWeight.Medium, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_Regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_SemiBold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_SemiBoldItalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMono_Thin, weight = FontWeight.Thin, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMono_ThinItalic, weight = FontWeight.Thin, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_BoldItalic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_ExtraBold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_ExtraBoldItalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_ExtraLight, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_ExtraLightItalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Light, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_LightItalic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_MediumItalic, weight = FontWeight.Medium, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_SemiBold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_SemiBoldItalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
        Font(Res.font.JetBrainsMonoNL_Thin, weight = FontWeight.Thin, style = FontStyle.Normal),
        Font(Res.font.JetBrainsMonoNL_ThinItalic, weight = FontWeight.Thin, style = FontStyle.Italic),
    )
