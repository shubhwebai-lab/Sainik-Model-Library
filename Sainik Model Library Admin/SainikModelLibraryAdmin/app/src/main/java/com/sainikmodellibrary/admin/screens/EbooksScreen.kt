package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.Ebook
import com.sainikmodellibrary.admin.data.demoEbooks
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SecondaryActionButton
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — E-books Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी ई-पुस्तकें डेमो डेटा हैं।
 *  - "Add E-book" और "Remove" दबाने पर केवल सूचना dialog खुलता है।
 *  - वास्तविक PDF upload / download / delete के लिए backend service चाहिए —
 *    Version 1 में कोई फ़ाइल upload, download, या delete नहीं होता।
 */
@Composable
fun EbooksScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showUploadInfoDialog by remember { mutableStateOf(false) }
    var showRemoveInfoDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.ebooks_title),
            subtitle = stringResource(R.string.ebooks_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryActionButton(
                text = stringResource(R.string.ebooks_add_button),
                onClick = { showUploadInfoDialog = true },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (demoEbooks.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoEbooks.forEach { ebook ->
                    EbookCard(
                        ebook = ebook,
                        onRemoveClick = { showRemoveInfoDialog = true }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // ===== Upload info dialog =====
    if (showUploadInfoDialog) {
        EbookInfoDialog(
            title = stringResource(R.string.ebook_upload_placeholder),
            message = stringResource(R.string.ebook_demo_notice),
            onDismiss = { showUploadInfoDialog = false }
        )
    }

    // ===== Remove info dialog =====
    if (showRemoveInfoDialog) {
        EbookInfoDialog(
            title = stringResource(R.string.ebook_remove),
            message = stringResource(R.string.ebook_demo_notice),
            onDismiss = { showRemoveInfoDialog = false }
        )
    }
}

/**
 * एक ई-पुस्तक का कार्ड — शीर्षक, आकार, अपलोड तिथि, और Remove बटन।
 */
@Composable
private fun EbookCard(
    ebook: Ebook,
    onRemoveClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // PDF icon
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SmlSurfaceElevated,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = SmlRed,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ebook.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.ebook_size) + ": " + ebook.size,
                        style = MaterialTheme.typography.bodySmall,
                        color = SmlTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            SmlDivider()
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.ebook_uploaded_on) + ": " + ebook.uploadedOn,
                    style = MaterialTheme.typography.labelSmall,
                    color = SmlTextMuted
                )
                SecondaryActionButton(
                    text = stringResource(R.string.ebook_remove),
                    onClick = onRemoveClick
                )
            }
        }
    }
}

/**
 * साझा जानकारी dialog — upload और remove — दोनों के लिए।
 * केवल "Close" बटन — कोई fake success नहीं।
 */
@Composable
private fun EbookInfoDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = SmlSurfaceElevated
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextMuted
                )
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryActionButton(
                    text = stringResource(R.string.close),
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}