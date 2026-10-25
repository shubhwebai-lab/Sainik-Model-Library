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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.AdmissionApplication
import com.sainikmodellibrary.admin.data.AdmissionStatus
import com.sainikmodellibrary.admin.data.demoAdmissions
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.DetailRow
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.FilterChipRow
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SecondaryActionButton
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning

/**
 * Sainik Model Library Admin — Admission Applications Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी आवेदन डेमो डेटा हैं।
 *  - कार्ड पर टैप करने से detail dialog खुलता है (read-only)।
 *  - "Approve" और "Reject" बटन जान-बूझकर **disabled** हैं — क्लिक पर कुछ नहीं होता।
 *  - कोई आवेदन वास्तव में स्वीकृत, अस्वीकृत या सर्वर पर नहीं भेजा जाता।
 */
@Composable
fun AdmissionsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilterIndex by remember { mutableStateOf(0) }
    var selectedApplication by remember { mutableStateOf<AdmissionApplication?>(null) }

    val filterLabels = listOf(
        stringResource(R.string.admissions_filter_all),
        stringResource(R.string.admissions_filter_pending),
        stringResource(R.string.admissions_filter_approved),
        stringResource(R.string.admissions_filter_rejected)
    )

    val filteredApplications = demoAdmissions.filter { app ->
        when (selectedFilterIndex) {
            1 -> app.status == AdmissionStatus.PENDING
            2 -> app.status == AdmissionStatus.APPROVED
            3 -> app.status == AdmissionStatus.REJECTED
            else -> true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.admissions_title),
            subtitle = stringResource(R.string.admissions_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(12.dp))

            FilterChipRow(
                items = filterLabels,
                selectedIndex = selectedFilterIndex,
                onSelect = { selectedFilterIndex = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredApplications.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                filteredApplications.forEach { application ->
                    AdmissionCard(
                        application = application,
                        onClick = { selectedApplication = application }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // ===== Detail Dialog =====
    val activeApplication = selectedApplication
    if (activeApplication != null) {
        AdmissionDetailDialog(
            application = activeApplication,
            onDismiss = { selectedApplication = null }
        )
    }
}

/**
 * एक आवेदन का कार्ड — आवेदक का नाम, कक्षा, जमा तिथि, स्थिति chip।
 */
@Composable
private fun AdmissionCard(
    application: AdmissionApplication,
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = application.applicantName,
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = application.appliedClass,
                        style = MaterialTheme.typography.bodySmall,
                        color = SmlTextMuted
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                StatusChip(
                    text = admissionStatusLabel(application.status),
                    color = admissionStatusColor(application.status)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.admission_submitted_on) +
                        ": " + application.submittedOn,
                style = MaterialTheme.typography.labelSmall,
                color = SmlTextMuted
            )
        }
    }
}

/**
 * Detail dialog — केवल पढ़ने के लिए। Approve/Reject बटन disabled हैं।
 */
@Composable
private fun AdmissionDetailDialog(
    application: AdmissionApplication,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = SmlSurfaceElevated
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.admission_detail_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(
                    label = stringResource(R.string.admission_applicant),
                    value = application.applicantName
                )
                DetailRow(
                    label = stringResource(R.string.admission_class),
                    value = application.appliedClass
                )
                DetailRow(
                    label = stringResource(R.string.admission_submitted_on),
                    value = application.submittedOn
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.admission_status),
                        style = MaterialTheme.typography.bodyMedium,
                        color = SmlTextMuted
                    )
                    StatusChip(
                        text = admissionStatusLabel(application.status),
                        color = admissionStatusColor(application.status)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(12.dp))

                DemoDataBanner(text = stringResource(R.string.admission_demo_notice))

                Spacer(modifier = Modifier.height(16.dp))

                // Approve/Reject — जान-बूझकर disabled
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PrimaryActionButton(
                        text = stringResource(R.string.admission_approve),
                        onClick = { /* demo-only: कोई क्रिया नहीं */ },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    )
                    SecondaryActionButton(
                        text = stringResource(R.string.admission_reject),
                        onClick = { /* demo-only: कोई क्रिया नहीं */ },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

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
 * Status enum → localized string।
 */
@Composable
private fun admissionStatusLabel(status: AdmissionStatus): String = when (status) {
    AdmissionStatus.PENDING -> stringResource(R.string.status_pending)
    AdmissionStatus.APPROVED -> stringResource(R.string.status_approved)
    AdmissionStatus.REJECTED -> stringResource(R.string.status_rejected)
}

/**
 * Status enum → chip रंग।
 * PENDING → warning (amber), APPROVED → success (green), REJECTED → primary red।
 */
private fun admissionStatusColor(status: AdmissionStatus): Color = when (status) {
    AdmissionStatus.PENDING -> SmlWarning
    AdmissionStatus.APPROVED -> SmlSuccess
    AdmissionStatus.REJECTED -> SmlRed
}