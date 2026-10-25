package com.sainikmodellibrary.admin.screens

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.DetailRow
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.components.SimpleFilterChip
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — Settings Screen
 *
 * यह स्क्रीन भाषा-स्विच को कार्यान्वित करती है (हिंदी ↔ अंग्रेज़ी)।
 *
 * ── भाषा-स्विच कैसे काम करता है ──
 *  - AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("hi"|"en"))
 *  - API 33+ → platform per-app language API का उपयोग होता है
 *  - API 26–32 → androidx.appcompat का backport इस्तेमाल होता है, जो activity को recreate करता है
 *  - भाषा-परिवर्तन के बाद screen फिर से बनती है और नई भाषा में दिखती है।
 *  - ⚠️ Version 1 में भाषा "persist" नहीं होती (बिना autoStoreLocales service के);
 *    यह केवल current session में लागू रहती है — जैसा strings.xml के notice में भी बताया गया है।
 *
 * ⚠️ बाकी सभी settings केवल UI प्रदर्शन हैं।
 */
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // वर्तमान भाषा पढ़ें। खाली होने का मतलब default — जो हमने MainActivity में हिंदी सेट की है।
    val currentLocales = AppCompatDelegate.getApplicationLocales()
    val currentLanguageTag = currentLocales.toLanguageTags()
    val isHindiSelected = currentLanguageTag.isEmpty() || currentLanguageTag.startsWith("hi")

    // Version name — PackageManager से (कोई BuildConfig की ज़रूरत नहीं)
    val versionName = remember(context) {
        try {
            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
            info.versionName ?: "1.0.0"
        } catch (_: Exception) {
            "1.0.0"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.settings_title),
            subtitle = stringResource(R.string.settings_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // ===== Language section =====
            SectionHeading(text = stringResource(R.string.settings_language))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SimpleFilterChip(
                    text = stringResource(R.string.settings_language_hindi),
                    selected = isHindiSelected,
                    onClick = {
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags("hi")
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                SimpleFilterChip(
                    text = stringResource(R.string.settings_language_english),
                    selected = !isHindiSelected,
                    onClick = {
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags("en")
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DemoDataBanner(text = stringResource(R.string.settings_local_pref_notice))

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Theme section =====
            SectionHeading(text = stringResource(R.string.settings_theme))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                DetailRow(
                    label = stringResource(R.string.settings_theme),
                    value = stringResource(R.string.settings_theme_dark)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.dashboard_welcome_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = SmlTextMuted,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ===== App Information =====
            SectionHeading(text = stringResource(R.string.settings_app_info))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(
                    label = stringResource(R.string.version_label),
                    value = versionName
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}