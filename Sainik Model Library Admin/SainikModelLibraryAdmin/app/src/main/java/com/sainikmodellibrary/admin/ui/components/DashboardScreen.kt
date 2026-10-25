package com.sainikmodellibrary.admin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.demoDashboardSummary
import com.sainikmodellibrary.admin.data.demoProfile
import com.sainikmodellibrary.admin.data.demoRecentActivities
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning

/**
 * Sainik Model Library Admin — Dashboard Screen
 *
 * यह पहली असली स्क्रीन है जो theme, components और demo data — तीनों को जोड़कर
 * एक पूरा प्रीमियम लुक देती है।
 *
 * यह स्क्रीन कुछ भी "persist" नहीं करती — जो भी क्रिया (summary card tap, quick action)
 * वह केवल दूसरी स्क्रीन पर navigation trigger करती है (callback के माध्यम से)।
 *
 * ── Navigation callbacks ──
 * प्रत्येक quick action और summary card अपने respective screen पर जाने के लिए
 * एक lambda लेता है। इन lambdas को [navigation/AdminNavigation.kt] में implement किया जाएगा।
 */
@Composable
fun DashboardScreen(
    onNavigateToStudents: () -> Unit,
    onNavigateToAdmissions: () -> Unit,
    onNavigateToNotices: () -> Unit,
    onNavigateToAttendance: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // ===== 1. Welcome Header =====
        Text(
            text = stringResource(R.string.dashboard_welcome),
            style = MaterialTheme.typography.bodyMedium,
            color = SmlTextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = demoProfile.name,
            style = MaterialTheme.typography.headlineMedium,
            color = SmlTextPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.dashboard_welcome_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = SmlTextMuted
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ===== 2. Demo Data Banner =====
        DemoDataBanner(text = stringResource(R.string.demo_only_notice))

        Spacer(modifier = Modifier.height(20.dp))

        // ===== 3. Summary Cards (2 x 2 grid) =====
        // Row 1 — Students | Pending Admissions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                label = stringResource(R.string.dashboard_card_students),
                value = demoDashboardSummary.totalStudents.toString(),
                accentColor = SmlRed,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToStudents
            )
            SummaryCard(
                label = stringResource(R.string.dashboard_card_pending_admissions),
                value = demoDashboardSummary.pendingAdmissions.toString(),
                accentColor = SmlWarning,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToAdmissions
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 2 — Notices | Attendance Today
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                label = stringResource(R.string.dashboard_card_notices),
                value = demoDashboardSummary.activeNotices.toString(),
                accentColor = SmlRed,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToNotices
            )
            SummaryCard(
                label = stringResource(R.string.dashboard_card_attendance),
                value = demoDashboardSummary.attendanceToday.toString(),
                accentColor = SmlSuccess,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToAttendance
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== 4. Quick Actions =====
        SectionHeading(text = stringResource(R.string.dashboard_section_quick_actions))
        Spacer(modifier = Modifier.height(8.dp))

        // Row 1 — Students | Admissions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardQuickAction(
                icon = Icons.Default.People,
                label = stringResource(R.string.nav_students),
                onClick = onNavigateToStudents,
                modifier = Modifier.weight(1f)
            )
            DashboardQuickAction(
                icon = Icons.Default.Description,
                label = stringResource(R.string.nav_admissions),
                onClick = onNavigateToAdmissions,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 2 — Notices | Attendance
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardQuickAction(
                icon = Icons.Default.Notifications,
                label = stringResource(R.string.nav_notices),
                onClick = onNavigateToNotices,
                modifier = Modifier.weight(1f)
            )
            DashboardQuickAction(
                icon = Icons.Default.CheckCircle,
                label = stringResource(R.string.nav_attendance),
                onClick = onNavigateToAttendance,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== 5. Recent Activity =====
        SectionHeading(text = stringResource(R.string.dashboard_section_recent_activity))
        Spacer(modifier = Modifier.height(8.dp))

        demoRecentActivities.forEach { activity ->
            ContentCard(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SmlTextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activity.timestamp,
                    style = MaterialTheme.typography.labelMedium,
                    color = SmlTextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Dashboard के अंदर उपयोग होने वाला छोटा action card।
 * यह private है क्योंकि केवल Dashboard की ज़रूरत के लिए बनाया गया है।
 * (अगर भविष्य में दूसरी स्क्रीन्स को भी ऐसा चाहिए, तब इसे CommonComponents
 * में स्थानांतरित करेंगे।)
 */
@Composable
private fun DashboardQuickAction(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = SmlSurfaceElevated
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SmlRed,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = SmlTextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}