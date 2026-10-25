package com.sainikmodellibrary.admin.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Sainik Model Library Admin — Theme
 *
 * यह पूरे ऐप की एकमात्र Theme है। इसमें हम:
 *  1. सभी 7 मुख्य रंगों को Material 3 [darkColorScheme] में मैप करते हैं।
 *  2. Status bar और Navigation bar के icons को dark background के अनुसार सही रंग देते हैं।
 *  3. [SmlTypography] लगाते हैं।
 *
 * ऐप डिज़ाइन हमेशा dark है (आपके design system के अनुसार)।
 * यह function ही [MainActivity] में पूरे UI को wrap करेगा।
 */
private val SmlDarkColorScheme = darkColorScheme(
    // ===== Primary (brand red) =====
    primary = SmlRed,
    onPrimary = SmlTextPrimary,
    primaryContainer = SmlRedMuted,
    onPrimaryContainer = SmlRed,

    // ===== Secondary (muted gray) =====
    secondary = SmlTextMuted,
    onSecondary = SmlBackground,
    secondaryContainer = SmlSurfaceElevated,
    onSecondaryContainer = SmlTextPrimary,

    // ===== Tertiary (success green, unused primary but available) =====
    tertiary = SmlSuccess,
    onTertiary = SmlBackground,

    // ===== Backgrounds =====
    background = SmlBackground,
    onBackground = SmlTextPrimary,

    // ===== Surfaces =====
    surface = SmlSurface,
    onSurface = SmlTextPrimary,
    surfaceVariant = SmlSurfaceElevated,
    onSurfaceVariant = SmlTextMuted,

    // ===== Errors (red पर आधारित) =====
    error = SmlRed,
    onError = SmlTextPrimary,
    errorContainer = SmlRedMuted,
    onErrorContainer = SmlRed,

    // ===== Outlines / dividers =====
    outline = SmlDivider,
    outlineVariant = SmlDivider
)

@Composable
fun SainikModelLibraryAdminTheme(
    // डिज़ाइन हमेशा dark है, इसलिए यह parameter सिर्फ़ अनुमति के लिए है
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // अगर भविष्य में light theme चाहिए, तो यहीं जोड़ सकते हैं। अभी हमेशा dark।
    @Suppress("UNUSED_EXPRESSION")
    isSystemInDarkTheme() // (silence unused import warning — कोई असर नहीं)

    val colorScheme = SmlDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // System bars को हमारे background के रंग के साथ मिलाएँ
            window.statusBarColor = SmlBackground.toArgb()
            window.navigationBarColor = SmlBackground.toArgb()

            // Status bar के icons सफ़ेद (dark bg पर दिखें)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            // Navigation bar के icons भी सफ़ेद
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SmlTypography,
        content = content
    )
}