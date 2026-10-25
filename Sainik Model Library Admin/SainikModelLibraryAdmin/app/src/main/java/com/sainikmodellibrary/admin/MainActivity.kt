package com.sainikmodellibrary.admin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sainikmodellibrary.admin.navigation.AdminNavigation
import com.sainikmodellibrary.admin.ui.theme.SainikModelLibraryAdminTheme

/**
 * Sainik Model Library Admin — MainActivity
 *
 * ── यह फ़ाइल क्या करती है ──
 *  1. ऐप की एकमात्र Activity है (Compose single-activity pattern)।
 *  2. पहली बार launch होने पर डिफ़ॉल्ट भाषा = हिंदी सेट करती है।
 *     (Settings में user चाहे तो अंग्रेज़ी चुन सकता है।)
 *  3. पूरी UI को [SainikModelLibraryAdminTheme] से wrap करती है, जिससे
 *     सभी स्क्रीन्स को हमारी थीम (रंग + typography) मिल जाती है।
 *  4. [AdminNavigation] render करती है — जिसमें सभी 12 स्क्रीन्स हैं।
 *
 * ── क्यों AppCompatActivity (ComponentActivity नहीं)? ──
 *     In-app language switching (हिंदी ↔ अंग्रेज़ी) के लिए AppCompat की
 *     per-app language backport ज़रूरी है। यह API 26 से 32 तक काम करती है।
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // ⚠️ सबसे पहले भाषा-डिफ़ॉल्ट लगाएँ, super.onCreate से पहले।
        // क्यों? → क्योंकि locale activity बनने से पहले ही तय होनी चाहिए,
        // ताकि strings.xml का सही भाषा-वर्ज़न लोड हो।
        applyDefaultLanguageIfNeeded()

        super.onCreate(savedInstanceState)

        setContent {
            SainikModelLibraryAdminTheme {
                AdminNavigation()
            }
        }
    }

    /**
     * अगर user ने पहले कोई भाषा नहीं चुनी (Locales खाली हैं), तो हिंदी default करें।
     * जब user Settings में English चुनेगा, तो Locales = [en] हो जाएँगी
     * और यह function कुछ नहीं बदलेगा।
     */
    private fun applyDefaultLanguageIfNeeded() {
        val current = AppCompatDelegate.getApplicationLocales()
        if (current.isEmpty) {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("hi")
            )
        }
    }
}