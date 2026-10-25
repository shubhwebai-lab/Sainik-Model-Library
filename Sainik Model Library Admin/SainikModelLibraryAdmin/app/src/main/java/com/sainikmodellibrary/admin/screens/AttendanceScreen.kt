package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.AttendanceRecord
import com.sainikmodellibrary.admin.data.AttendanceStatus
import com.sainikmodellibrary.admin.data.demoAttendance
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.components.SummaryCard
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning

/**
 * Sainik Model Library Admin — Attendance Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी उपस्थिति रिकॉर्ड डेमो डेटा हैं।
 *  - उपस्थिति लाइव ट्रैक नहीं हो रही है।
 *  - QR आधारित चेक-इन Version 1 में लागू नहीं है।
 *  - GPS आधारित उपस्थिति Version 1 में लागू नहीं है।
 *
 *  स्क्रीन के दो हिस्से हैं:
 *   1. आज का सारांश (Present / Absent / Late counts)
 *   2. आज के रिकॉर्ड (हर छात्र की check-in / check-out समय)
 */
@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // सारांश गणना — सभी demoAttendance से
    val presentCount = demoAttendance.count { it.status == AttendanceStatus.PRESENT }
    val absentCount = demoAttendance.count { it.status == AttendanceStatus.ABSENT }
    val lateCount = demoAttendance.count { it.status == AttendanceStatus.LATE }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.attendance_title),
            subtitle = stringResource(R.string.attendance_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(16.dp))

            // ===== आज का सारांश =====
            SectionHeading(text = stringResource(R.string.attendance_today_summary))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    label = stringResource(R.string.attendance_present),
                    value = presentCount.toString(),
                    accentColor = SmlSuccess,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    label = stringResource(R.string.attendance_absent),
                    value = absentCount.toString(),
                    accentColor = SmlRed,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    label = stringResource(R.string.attendance_late),
                    value = lateCount.toString(),
                    accentColor = SmlWarning,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== QR / GPS सूचना =====
            DemoDataBanner(text = stringResource(R.string.attendance_qr_notice))
            Spacer(modifier = Modifier.height(8.dp))
            DemoDataBanner(text = stringResource(R.string.attendance_gps_notice))

            Spacer(modifier = Modifier.height(20.dp))

            // ===== आज के रिकॉर्ड =====
            SectionHeading(text = stringResource(R.string.attendance_records_title))

            Spacer(modifier = Modifier.height(8.dp))

            if (demoAttendance.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoAttendance.forEach { record ->
                    AttendanceRecordCard(record = record)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            DemoDataBanner(text = stringResource(R.string.attendance_demo_notice))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * एक छात्र के उपस्थिति का कार्ड — नाम, कक्षा, status chip, check-in/check-out।
 */
@Composable
private fun AttendanceRecordCard(record: AttendanceRecord) {
    ContentCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.studentName,
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = record.className,
                    style = MaterialTheme.typography.bodySmall,
                    color = SmlTextMuted
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            StatusChip(
                text = attendanceStatusLabel(record.status),
                color = attendanceStatusColor(record.status)
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
                    text = stringResource(R.string.attendance_check_in),
                    style = MaterialTheme.typography.labelSmall,
                    color = SmlTextMuted
                )
                Text(
                    text = record.checkIn,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.attendance_check_out),
                    style = MaterialTheme.typography.labelSmall,
                    color = SmlTextMuted
                )
                Text(
                    text = record.checkOut,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary
                )
            }
        }
    }
}

/**
 * AttendanceStatus enum → localized string।
 */
@Composable
private fun attendanceStatusLabel(status: AttendanceStatus): String = when (status) {
    AttendanceStatus.PRESENT -> stringResource(R.string.attendance_present)
    AttendanceStatus.ABSENT -> stringResource(R.string.attendance_absent)
    AttendanceStatus.LATE -> stringResource(R.string.attendance_late)
}

/**
 * AttendanceStatus enum → chip रंग।
 * PRESENT → success, ABSENT → primary red, LATE → warning।
 */
private fun attendanceStatusColor(status: AttendanceStatus): Color = when (status) {
    AttendanceStatus.PRESENT -> SmlSuccess
    AttendanceStatus.ABSENT -> SmlRed
    AttendanceStatus.LATE -> SmlWarning
}