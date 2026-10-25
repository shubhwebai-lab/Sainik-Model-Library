package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.WebsiteSection
import com.sainikmodellibrary.admin.data.demoWebsiteSections
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SecondaryActionButton
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.theme.SmlDivider as SmlDividerBorderColor
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — Website Content Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी website sections डेमो डेटा हैं।
 *  - "Save Draft" और "Publish to Website" दबाने पर केवल एक demo confirmation दिखती है।
 *  - कोई वास्तविक वेबसाइट अपडेट नहीं होती।
 *  - कोई backend / API call नहीं होती।
 *
 * स्क्रीन के भाग:
 *   1. Website sections की सूची (Home, About, Contact, Gallery)
 *   2. किसी भी section पर टैप → Edit dialog (live preview के साथ)
 *   3. Save/Publish → demo confirmation
 */
@Composable
fun WebsiteContentScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var editingSection by remember { mutableStateOf<WebsiteSection?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.website_content_title),
            subtitle = stringResource(R.string.website_content_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeading(text = stringResource(R.string.website_content_edit_title))

            Spacer(modifier = Modifier.height(8.dp))

            if (demoWebsiteSections.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoWebsiteSections.forEach { section ->
                    WebsiteSectionCard(
                        section = section,
                        onClick = { editingSection = section }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            DemoDataBanner(text = stringResource(R.string.website_content_demo_notice))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    val activeSection = editingSection
    if (activeSection != null) {
        EditSectionDialog(
            section = activeSection,
            onDismiss = { editingSection = null }
        )
    }
}

/**
 * एक website section का कार्ड — localized नाम, content preview, और edit icon।
 */
@Composable
private fun WebsiteSectionCard(
    section: WebsiteSection,
    onClick: () -> Unit
) {
    val displayName = sectionDisplayName(section)

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Globe icon
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SmlSurfaceElevated,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
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
                        text = displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = section.content,
                        style = MaterialTheme.typography.bodySmall,
                        color = SmlTextMuted,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = SmlTextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Edit dialog — दो states:
 *  1. Edit form (content field + live preview + Save/Publish/Cancel)
 *  2. Demo confirmation (Save/Publish दबाने के बाद)
 */
@Composable
private fun EditSectionDialog(
    section: WebsiteSection,
    onDismiss: () -> Unit
) {
    var editedContent by remember(section.id) { mutableStateOf(section.content) }
    var showDemoConfirmation by remember(section.id) { mutableStateOf(false) }

    val displayName = sectionDisplayName(section)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 640.dp),
            shape = RoundedCornerShape(20.dp),
            color = SmlSurfaceElevated
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                if (showDemoConfirmation) {
                    // ===== Demo confirmation state =====
                    Text(
                        text = stringResource(R.string.website_content_publish_action),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.website_content_demo_notice),
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
                    // ===== Edit form state =====
                    Text(
                        text = stringResource(R.string.website_content_edit_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SmlTextMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Editable content field
                    OutlinedTextField(
                        value = editedContent,
                        onValueChange = { editedContent = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        maxLines = 8,
                        shape = RoundedCornerShape(12.dp),
                        label = {
                            Text(
                                text = displayName,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Live preview
                    Text(
                        text = stringResource(R.string.website_content_preview_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = SmlSurface,
                        border = BorderStroke(1.dp, SmlDividerBorderColor)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.titleSmall,
                                color = SmlTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (editedContent.isBlank()) "…" else editedContent,
                                style = MaterialTheme.typography.bodySmall,
                                color = SmlTextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    DemoDataBanner(text = stringResource(R.string.website_content_demo_notice))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SecondaryActionButton(
                            text = stringResource(R.string.website_content_save_draft),
                            onClick = { showDemoConfirmation = true },
                            modifier = Modifier.weight(1f)
                        )
                        PrimaryActionButton(
                            text = stringResource(R.string.website_content_publish_action),
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
 * sectionKey के आधार पर स्थानीयकृत नाम लौटाता है।
 * अगर key मेल न खाए, तो section.title (डेमो नाम) का उपयोग होता है।
 */
@Composable
private fun sectionDisplayName(section: WebsiteSection): String = when (section.sectionKey) {
    "home" -> stringResource(R.string.website_section_home)
    "about" -> stringResource(R.string.website_section_about)
    "contact" -> stringResource(R.string.website_section_contact)
    "gallery" -> stringResource(R.string.website_section_gallery)
    else -> section.title
}