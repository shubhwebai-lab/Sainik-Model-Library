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
import androidx.compose.material.icons.filled.Person
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
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.Student
import com.sainikmodellibrary.admin.data.StudentStatus
import com.sainikmodellibrary.admin.data.demoStudents
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.FilterChipRow
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SearchField
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — Students Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - "Add Student" बटन केवल एक सूचना दिखाता है (कोई फ़ॉर्म नहीं खुलता)।
 *  - Search और Filter स्थानीय रूप से काम करते हैं (कोई डेटाबेस नहीं)।
 *  - सभी छात्र डेमो डेटा हैं।
 *
 * Search और Filter state पर-स्क्रीन (remember) हैं — जब स्क्रीन छोड़कर वापस आएँगे
 * तो ये रीसेट हो जाएँगे। Version 1 में यह जान-बूझकर है ताकि कोई false persistence दावा न हो।
 */
@Composable
fun StudentsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterIndex by remember { mutableStateOf(0) }
    var showDemoAddMessage by remember { mutableStateOf(false) }

    // Filter labels
    val filterLabels = listOf(
        stringResource(R.string.students_filter_all),
        stringResource(R.string.students_filter_active),
        stringResource(R.string.students_filter_inactive)
    )

    // Search + filter logics
    val filteredStudents: List<Student> = demoStudents.filter { student ->
        val matchesSearch = searchQuery.isBlank() ||
                student.name.contains(searchQuery, ignoreCase = true) ||
                student.className.contains(searchQuery, ignoreCase = true) ||
                student.rollNumber.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilterIndex) {
            0 -> true // All
            1 -> student.status == StudentStatus.ACTIVE
            2 -> student.status == StudentStatus.INACTIVE
            else -> true
        }

        matchesSearch && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // ===== Header =====
        AdminScreenHeader(
            title = stringResource(R.string.students_title),
            subtitle = stringResource(R.string.students_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // ===== Demo banner =====
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Add button (demo only) =====
            PrimaryActionButton(
                text = stringResource(R.string.students_add_button),
                onClick = { showDemoAddMessage = true },
                modifier = Modifier.fillMaxWidth()
            )

            if (showDemoAddMessage) {
                Spacer(modifier = Modifier.height(8.dp))
                DemoDataBanner(text = stringResource(R.string.demo_only_notice))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Search =====
            SearchField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = stringResource(R.string.students_search)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ===== Filter chips =====
            FilterChipRow(
                items = filterLabels,
                selectedIndex = selectedFilterIndex,
                onSelect = { selectedFilterIndex = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ===== Student list =====
            if (filteredStudents.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                filteredStudents.forEach { student ->
                    StudentCard(student = student)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * एक छात्र का कार्ड — नाम, कक्षा, रोल, फ़ोन, status.
 */
@Composable
private fun StudentCard(student: Student) {
    ContentCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar circle
            Surface(
                shape = RoundedCornerShape(50),
                color = SmlSurfaceElevated,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
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
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${student.className}  •  Roll ${student.rollNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = SmlTextMuted
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Status chip
            val isActive = student.status == StudentStatus.ACTIVE
            StatusChip(
                text = if (isActive) {
                    stringResource(R.string.status_active)
                } else {
                    stringResource(R.string.status_inactive)
                },
                color = if (isActive) SmlSuccess else SmlTextMuted
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        SmlDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.student_phone),
                    style = MaterialTheme.typography.labelSmall,
                    color = SmlTextMuted
                )
                Text(
                    text = student.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.student_added_on),
                    style = MaterialTheme.typography.labelSmall,
                    color = SmlTextMuted
                )
                Text(
                    text = student.addedOn,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary
                )
            }
        }
    }
}