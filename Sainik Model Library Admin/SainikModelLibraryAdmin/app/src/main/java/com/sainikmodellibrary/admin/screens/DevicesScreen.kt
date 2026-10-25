package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.PhoneAndroid
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.DeviceInfo
import com.sainikmodellibrary.admin.data.DeviceStatus
import com.sainikmodellibrary.admin.data.demoDevices
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.DetailRow
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.components.SummaryCard
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning

/**
 * Sainik Model Library Admin — Devices Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - सभी डिवाइस डेमो डेटा हैं।
 *  - "Register Device" बटन केवल एक सूचना dialog खोलता है।
 *  - कोई डिवाइस वास्तव में पंजीकृत, सत्यापित, स्वीकृत या अवरुद्ध नहीं होता।
 *  - "अधिकतम 2 स्वीकृत डिवाइस" policy केवल placeholder है।
 */
@Composable
fun DevicesScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showRegisterDialog by remember { mutableStateOf(false) }

    // Policy के लिए गणना
    val approvedCount = demoDevices.count { it.status == DeviceStatus.APPROVED }
    val pendingCount = demoDevices.count { it.status == DeviceStatus.PENDING }
    val blockedCount = demoDevices.count { it.status == DeviceStatus.BLOCKED }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.devices_title),
            subtitle = stringResource(R.string.devices_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(16.dp))

            // ===== Policy Summary Cards =====
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    label = stringResource(R.string.device_status_approved),
                    value = approvedCount.toString(),
                    accentColor = SmlSuccess,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    label = stringResource(R.string.device_status_pending),
                    value = pendingCount.toString(),
                    accentColor = SmlWarning,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    label = stringResource(R.string.device_status_blocked),
                    value = blockedCount.toString(),
                    accentColor = SmlRed,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ===== Policy placeholder banner =====
            MaxDevicePolicyBanner(text = stringResource(R.string.devices_max_devices_policy))

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Register button =====
            PrimaryActionButton(
                text = stringResource(R.string.devices_register_button),
                onClick = { showRegisterDialog = true },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Registered Devices =====
            SectionHeading(text = stringResource(R.string.devices_registered_title))

            Spacer(modifier = Modifier.height(8.dp))

            if (demoDevices.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoDevices.forEach { device ->
                    DeviceCard(device = device)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            DemoDataBanner(text = stringResource(R.string.devices_demo_notice))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // ===== Register device demo dialog =====
    if (showRegisterDialog) {
        Dialog(onDismissRequest = { showRegisterDialog = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = SmlSurfaceElevated
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = stringResource(R.string.devices_register_button),
                        style = MaterialTheme.typography.titleLarge,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.devices_demo_notice),
                        style = MaterialTheme.typography.bodyMedium,
                        color = SmlTextMuted
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    PrimaryActionButton(
                        text = stringResource(R.string.close),
                        onClick = { showRegisterDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * एक डिवाइस का कार्ड — नाम, मॉडल, status chip, last active।
 */
@Composable
private fun DeviceCard(device: DeviceInfo) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SmlSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SmlSurfaceElevated,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = deviceStatusColor(device.status),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.deviceName,
                        style = MaterialTheme.typography.titleMedium,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.device_model) + ": " + device.model,
                        style = MaterialTheme.typography.bodySmall,
                        color = SmlTextMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                StatusChip(
                    text = deviceStatusLabel(device.status),
                    color = deviceStatusColor(device.status)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            SmlDivider()
            Spacer(modifier = Modifier.height(8.dp))

            DetailRow(
                label = stringResource(R.string.device_last_active),
                value = device.lastActive
            )
        }
    }
}

/**
 * "Maximum two approved devices" policy placeholder।
 * यह जान-बूझकर एक dashed-border info banner है — visual रूप से स्पष्ट कि यह
 * एक policy placeholder है, लागू नहीं है।
 */
@Composable
private fun MaxDevicePolicyBanner(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = SmlSurfaceElevated,
        border = BorderStroke(1.dp, SmlWarning.copy(alpha = 0.6f))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = SmlWarning,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

/**
 * DeviceStatus enum → localized string।
 */
@Composable
private fun deviceStatusLabel(status: DeviceStatus): String = when (status) {
    DeviceStatus.APPROVED -> stringResource(R.string.device_status_approved)
    DeviceStatus.PENDING -> stringResource(R.string.device_status_pending)
    DeviceStatus.BLOCKED -> stringResource(R.string.device_status_blocked)
}

/**
 * DeviceStatus enum → chip रंग।
 * APPROVED → success, PENDING → warning, BLOCKED → primary red।
 */
private fun deviceStatusColor(status: DeviceStatus): Color = when (status) {
    DeviceStatus.APPROVED -> SmlSuccess
    DeviceStatus.PENDING -> SmlWarning
    DeviceStatus.BLOCKED -> SmlRed
}