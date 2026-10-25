package com.sainikmodellibrary.admin.screens

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DetailRow
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — Help & About Screen
 *
 * यह स्क्रीन स्पष्ट बताती है:
 *  - ऐप का नाम और version
 *  - Version 1 का दायरा (UI-only, कोई backend/database नहीं)
 *  - Help और Support जानकारी
 *
 *  कोई संपर्क form, कोई email link, कोई fake support chat — कुछ नहीं।
 *  केवल जानकारी, जो उपयोगकर्ता वास्तव में पढ़ सके।
 */
@Composable
fun HelpAboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Version name — PackageManager से
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
            title = stringResource(R.string.help_about_title),
            subtitle = stringResource(R.string.help_about_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // ===== App name + version hero card =====
            ContentCard {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.help_about_version) + ": " + versionName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextMuted
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Version 1 Scope =====
            SectionHeading(text = stringResource(R.string.help_about_scope))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                Text(
                    text = stringResource(R.string.help_about_scope_details),
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Help & Support =====
            SectionHeading(text = stringResource(R.string.help_about_contact))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                DetailRow(
                    label = stringResource(R.string.help_about_contact),
                    value = stringResource(R.string.help_about_contact_details)
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(4.dp))
                DetailRow(
                    label = stringResource(R.string.version_label),
                    value = versionName
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== App details =====
            SectionHeading(text = stringResource(R.string.app_name))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                DetailRow(
                    label = stringResource(R.string.nav_settings),
                    value = stringResource(R.string.settings_theme_dark)
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(4.dp))
                DetailRow(
                    label = stringResource(R.string.settings_language),
                    value = stringResource(R.string.settings_language_hindi) +
                            " / " +
                            stringResource(R.string.settings_language_english)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}