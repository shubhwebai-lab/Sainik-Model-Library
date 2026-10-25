package com.sainikmodellibrary.admin.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.ui.theme.SmlBackground
import com.sainikmodellibrary.admin.ui.theme.SmlDisabled
import com.sainikmodellibrary.admin.ui.theme.SmlDivider
import com.sainikmodellibrary.admin.ui.theme.SmlOnDisabled
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning
import com.sainikmodellibrary.admin.ui.theme.SmlWarningMuted

/**
 * Sainik Model Library Admin — Reusable UI Components
 *
 * इस फ़ाइल में सभी स्क्रीन्स द्वारा उपयोग होने वाली "building blocks" हैं।
 * यहाँ के सभी components:
 *  - रंगों और typography के लिए theme का ही उपयोग करते हैं
 *  - user-visible text को parameters के रूप में लेते हैं (ताकि हर स्क्रीन हिंदी/अंग्रेज़ी में हो)
 *  - consistent shape, padding और hierarchy रखते हैं
 *
 * नियम: कोई भी screen अपना अलग card/chip/header नहीं बनाएगी — सब यहीं से लेंगी।
 */

// ============================================================
// 1. Screen Header — हर स्क्रीन के ऊपर
// ============================================================
@Composable
fun AdminScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = SmlTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = SmlTextPrimary,
                fontWeight = FontWeight.SemiBold
            )
            if (!subtitle.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextMuted
                )
            }
        }
    }
}

// ============================================================
// 2. Section Heading — content के अंदर उप-शीर्षक
// ============================================================
@Composable
fun SectionHeading(
    text: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = SmlTextPrimary
        )
        if (actionText != null && onAction != null) {
            OutlinedButton(
                onClick = onAction,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, SmlRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SmlRed)
            ) {
                Text(text = actionText, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

// ============================================================
// 3. Summary / Statistic Card — dashboard और overview
// ============================================================
@Composable
fun SummaryCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    accentColor: Color = SmlRed,
    onClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(16.dp)
    val content: @Composable ColumnScope.() -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .padding(end = 0.dp)
                    .let { it }
            ) {
                Surface(
                    color = accentColor,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .width(4.dp)
                        .height(40.dp)
                ) {}
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = SmlTextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (onClick != null) {
        Surface(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            color = SmlSurface
        ) { content() }
    } else {
        Surface(
            modifier = modifier,
            shape = shape,
            color = SmlSurface
        ) { content() }
    }
}

// ============================================================
// 4. Status Chip — Active/Pending/Approved आदि के लिए
// ============================================================
@Composable
fun StatusChip(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = color.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.40f))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = color,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

// ============================================================
// 5. Search Field — search input
// ============================================================
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(text = placeholder, style = MaterialTheme.typography.bodyMedium)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.cd_search)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SmlSurface,
            unfocusedContainerColor = SmlSurface,
            focusedBorderColor = SmlRed,
            unfocusedBorderColor = SmlDivider,
            focusedTextColor = SmlTextPrimary,
            unfocusedTextColor = SmlTextPrimary,
            cursorColor = SmlRed,
            focusedPlaceholderColor = SmlTextMuted,
            unfocusedPlaceholderColor = SmlTextMuted,
            focusedLeadingIconColor = SmlRed,
            unfocusedLeadingIconColor = SmlTextMuted
        )
    )
}

// ============================================================
// 6. Content Card — generic wrapper के लिए
// ============================================================
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

// ============================================================
// 7. Empty State — कोई item न मिले तो
// ============================================================
@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Inbox
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SmlTextMuted,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = SmlTextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = SmlTextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============================================================
// 8. Error State — असली एरर के लिए (Version 1 में कम उपयोग होगा)
// ============================================================
@Composable
fun ErrorState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    retryText: String,
    onRetry: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = SmlWarning,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = SmlTextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = SmlTextMuted,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryActionButton(text = retryText, onClick = onRetry)
        }
    }
}

// ============================================================
// 9. Loading Box — जब कुछ load हो रहा हो (Version 1 में सीमित उपयोग)
// ============================================================
@Composable
fun LoadingBox(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.loading)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = SmlRed)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = SmlTextMuted
        )
    }
}

// ============================================================
// 10. Demo Data Banner — "यह डेमो डेटा है" चेतावनी
// ============================================================
@Composable
fun DemoDataBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SmlWarningMuted,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SmlWarning.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SmlWarning,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = SmlWarning
            )
        }
    }
}

// ============================================================
// 11. Primary Action Button — मुख्य लाल बटन
// ============================================================
@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SmlRed,
            contentColor = SmlTextPrimary,
            disabledContainerColor = SmlDisabled,
            disabledContentColor = SmlOnDisabled
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

// ============================================================
// 12. Secondary Action Button — outline बटन
// ============================================================
@Composable
fun SecondaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (enabled) SmlRed else SmlDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = SmlRed,
            disabledContentColor = SmlOnDisabled
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

// ============================================================
// 13. Simple Filter Chip
// ============================================================
@Composable
fun SimpleFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) SmlRed else SmlSurfaceElevated
    val textColor = if (selected) SmlTextPrimary else SmlTextMuted

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = bgColor,
        border = BorderStroke(1.dp, if (selected) SmlRed else SmlDivider)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

// ============================================================
// 14. Filter Chip Row — horizontally scrollable
// ============================================================
@Composable
fun FilterChipRow(
    items: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEachIndexed { index, label ->
            SimpleFilterChip(
                text = label,
                selected = index == selectedIndex,
                onClick = { onSelect(index) }
            )
        }
    }
}

// ============================================================
// 15. Detail Row — label / value pair (profile, details के लिए)
// ============================================================
@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = SmlTextMuted,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = SmlTextPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.4f),
            textAlign = TextAlign.End
        )
    }
}

// ============================================================
// 16. Divider (theme-consistent)
// ============================================================
@Composable
fun SmlDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = SmlDivider
    )
}
// ============================================================
// सहायक — Content Card के अंदर उपयोग होने वाला spacing
// (इसे component नहीं, बस consistency के लिए रखा गया है)
// ============================================================
private val SmlCardInnerBackground = SmlBackground