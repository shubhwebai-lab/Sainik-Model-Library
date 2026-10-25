package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.Notice
import com.sainikmodellibrary.admin.data.NoticePriority
import com.sainikmodellibrary.admin.data.demoNotices
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.FilterChipRow
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SecondaryActionButton
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning
import com.sainikmodellibrary.admin.ui.theme.SmlDivider as SmlDividerBorderColor

/**
 * Sainik Model Library Admin — Notices Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी सूचनाएँ डेमो डेटा हैं।
 *  - "Create Notice" केवल एक फ़ॉर्म दिखाता है — Save Draft / Publish दबाने पर कुछ नहीं सहेजा या प्रकाशित होता।
 *  - Save/Publish दबाने पर "Demo only" confirmation screen दिखती है — कोई fake success नहीं।
 */
@Composable
fun NoticesScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNotice by remember { mutableStateOf<Notice?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.notices_title),
            subtitle = stringResource(R.string.notices_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryActionButton(
                text = stringResource(R.string.notices_create_button),
                onClick = { showCreateDialog = true },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (demoNotices.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoNotices.forEach { notice ->
                    NoticeCard(
                        notice = notice,
                        onClick = { selectedNotice = notice }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // ===== Detail Dialog =====
    val activeNotice = selectedNotice
    if (activeNotice != null) {
        NoticeDetailDialog(
            notice = activeNotice,
            onDismiss = { selectedNotice = null }
        )
    }

    // ===== Create Notice Dialog =====
    if (showCreateDialog) {
        CreateNoticeDialog(onDismiss = { showCreateDialog = false })
    }
}

/**
 * एक सूचना का कार्ड — शीर्षक, preview, priority, और प्रकाशन तिथि।
 */
@Composable
private fun NoticeCard(
    notice: Notice,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = notice.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatusChip(
                    text = noticePriorityLabel(notice.priority),
                    color = noticePriorityColor(notice.priority)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notice.body,
                style = MaterialTheme.typography.bodySmall,
                color = SmlTextMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))
            SmlDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.notice_publish_date) + ": " + notice.publishDate,
                style = MaterialTheme.typography.labelSmall,
                color = SmlTextMuted
            )
        }
    }
}

/**
 * Detail dialog — पूरा शीर्षक, priority, तिथि, और पूरा विवरण।
 */
@Composable
private fun NoticeDetailDialog(
    notice: Notice,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 560.dp),
            shape = RoundedCornerShape(20.dp),
            color = SmlSurfaceElevated
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.notice_preview_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusChip(
                        text = noticePriorityLabel(notice.priority),
                        color = noticePriorityColor(notice.priority)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.notice_publish_date) + ": " + notice.publishDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = SmlTextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = notice.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notice.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextMuted
                )

                Spacer(modifier = Modifier.height(16.dp))
                DemoDataBanner(text = stringResource(R.string.notice_demo_notice))
                Spacer(modifier = Modifier.height(16.dp))

                SecondaryActionButton(
                    text = stringResource(R.string.close),
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Create Notice form dialog — केवल UI। Save/Publish कुछ नहीं करते।
 * जब उपयोगकर्ता Save Draft या Publish दबाता है, तो dialog "demo confirmation" view पर जाता है।
 */
@Composable
private fun CreateNoticeDialog(
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var priorityIndex by remember { mutableStateOf(0) }
    var showDemoConfirmation by remember { mutableStateOf(false) }

    val priorityLabels = listOf(
        stringResource(R.string.notice_priority_normal),
        stringResource(R.string.notice_priority_high)
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 580.dp),
            shape = RoundedCornerShape(20.dp),
            color = SmlSurfaceElevated
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                if (showDemoConfirmation) {
                    // ===== Demo confirmation view =====
                    Text(
                        text = stringResource(R.string.demo_data_label),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.notice_demo_notice),
                        style = MaterialTheme.typography.bodyMedium,
                        color = SmlTextMuted
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    PrimaryActionButton(
                        text = stringResource(R.string.close),
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // ===== Form view =====
                    Text(
                        text = stringResource(R.string.notices_create_button),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    NoticeFormField(
                        value = title,
                        onValueChange = { title = it },
                        label = stringResource(R.string.notice_title_label)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    NoticeFormField(
                        value = bodyText,
                        onValueChange = { bodyText = it },
                        label = stringResource(R.string.notice_body_label),
                        singleLine = false,
                        minLines = 4
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = stringResource(R.string.notice_priority),
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FilterChipRow(
                        items = priorityLabels,
                        selectedIndex = priorityIndex,
                        onSelect = { priorityIndex = it }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    DemoDataBanner(text = stringResource(R.string.notice_demo_notice))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SecondaryActionButton(
                            text = stringResource(R.string.notice_save_draft),
                            onClick = { showDemoConfirmation = true },
                            modifier = Modifier.weight(1f)
                        )
                        PrimaryActionButton(
                            text = stringResource(R.string.notice_publish_action),
                            onClick = { showDemoConfirmation = true },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SecondaryActionButton(
                        text = stringResource(R.string.cancel),
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Create Notice form के अंदर उपयोग होने वाला एक styled text field।
 * (CommonComponents में reusable field नहीं है, इसलिए Notices में ही private रखा है।)
 */
@Composable
private fun NoticeFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        shape = RoundedCornerShape(12.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SmlSurface,
            unfocusedContainerColor = SmlSurface,
            focusedBorderColor = SmlRed,
            unfocusedBorderColor = SmlDividerBorderColor,
            focusedTextColor = SmlTextPrimary,
            unfocusedTextColor = SmlTextPrimary,
            cursorColor = SmlRed,
            focusedLabelColor = SmlRed,
            unfocusedLabelColor = SmlTextMuted
        )
    )
}

/**
 * Priority enum → localized string।
 */
@Composable
private fun noticePriorityLabel(priority: NoticePriority): String = when (priority) {
    NoticePriority.NORMAL -> stringResource(R.string.notice_priority_normal)
    NoticePriority.HIGH -> stringResource(R.string.notice_priority_high)
}

/**
 * Priority enum → chip रंग।
 */
private fun noticePriorityColor(priority: NoticePriority): Color = when (priority) {
    NoticePriority.NORMAL -> SmlTextMuted
    NoticePriority.HIGH -> SmlWarning
}