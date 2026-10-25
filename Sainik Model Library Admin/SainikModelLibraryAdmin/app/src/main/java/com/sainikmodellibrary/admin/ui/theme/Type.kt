package com.sainikmodellibrary.admin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Sainik Model Library Admin — Typography
 *
 * हम system default sans-serif font family का उपयोग कर रहे हैं ताकि:
 *  - कोई बाहरी font फ़ाइल project में न जोड़नी पड़े (budget ₹0, size कम)
 *  - हिंदी और अंग्रेज़ी दोनों script पर proper rendering मिले
 *  - Android 8.0+ (API 26) पर हर डिवाइस पर consistent दिखे
 *
 * सभी sizes/weights ऐसे चुने गए हैं कि mobile पर पढ़ने में आरामदायक हों
 * और dark background पर contrast बना रहे।
 */

private val SmlFontFamily = FontFamily.SansSerif

// ===== Base text styles =====
// इन्हें Material 3 के Typography slots पर मैप करेंगे।

private val DisplayLarge = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp
)

private val DisplayMedium = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp
)

private val HeadlineLarge = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
)

private val HeadlineMedium = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

private val TitleLarge = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp
)

private val TitleMedium = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.1.sp
)

private val BodyLarge = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.2.sp
)

private val BodyMedium = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.2.sp
)

private val BodySmall = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.3.sp
)

private val LabelLarge = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp
)

private val LabelMedium = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.4.sp
)

private val LabelSmall = TextStyle(
    fontFamily = SmlFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.5.sp
)

/**
 * Material 3 Typography — पूरे ऐप में Theme के ज़रिए उपयोग होगी।
 * UI में हम इन्हें [MaterialTheme.typography.headlineLarge] इत्यादि से पुकारेंगे।
 */
val SmlTypography = Typography(
    displayLarge = DisplayLarge,
    displayMedium = DisplayMedium,
    displaySmall = HeadlineLarge,        // displaySmall → headlineLarge पर मैप कर दिया
    headlineLarge = HeadlineLarge,
    headlineMedium = HeadlineMedium,
    headlineSmall = TitleLarge,
    titleLarge = TitleLarge,
    titleMedium = TitleMedium,
    titleSmall = LabelLarge,
    bodyLarge = BodyLarge,
    bodyMedium = BodyMedium,
    bodySmall = BodySmall,
    labelLarge = LabelLarge,
    labelMedium = LabelMedium,
    labelSmall = LabelSmall
)