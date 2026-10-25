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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.sainikmodellibrary.admin.data.AdminAccountStatus
import com.sainikmodellibrary.admin.data.AdminRole
import com.sainikmodellibrary.admin.data.AdminUser
import com.sainikmodellibrary.admin.data.demoAdmins
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.EmptyState
import com.sainikmodellibrary.admin.ui.components.PrimaryActionButton
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.components.StatusChip
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSuccess
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import com.sainikmodellibrary.admin.ui.theme.SmlWarning

/**
 * Sainik Model Library Admin — Admins Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - कोई वास्तविक एडमिन खाता नहीं बनाया जाता।
 *  - "Add Admin" बटन एक सूचना dialog खोलता है — कोई फ़ॉर्म नहीं।
 *  - कोई authentication या authorization नहीं है।
 */
@Composable
fun AdminsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.admins_title),
            subtitle = stringResource(R.string.admins_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryActionButton(
                text = stringResource(R.string.admins_add_button),
                onClick = { showAddDialog = true },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (demoAdmins.isEmpty()) {
                EmptyState(
                    title = stringResource(R.string.empty_state_title),
                    message = stringResource(R.string.empty_state_message)
                )
            } else {
                demoAdmins.forEach { admin ->
                    AdminCard(admin = admin)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // ===== Add Admin Demo Dialog =====
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            containerColor = SmlSurfaceElevated,
            titleContentColor = SmlTextPrimary,
            textContentColor = SmlTextMuted,
            title = {
                Text(
                    text = stringResource(R.string.admins_add_button),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.admin_add_demo_notice),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(
                        text = stringResource(R.string.close),
                        color = SmlRed,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

/**
 * एक एडमिन का कार्ड — नाम, ईमेल, role chip, status chip.
 */
@Composable
private fun AdminCard(admin: AdminUser) {
    ContentCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = SmlSurfaceElevated,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
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
                    text = admin.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = admin.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = SmlTextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
            // Role chip
            StatusChip(
                text = roleLabel(admin.role),
                color = when (admin.role) {
                    AdminRole.SUPER_ADMIN -> SmlRed
                    AdminRole.MANAGER -> SmlWarning
                    AdminRole.LIBRARIAN -> SmlTextMuted
                }
            )

            // Account status chip
            val isActive = admin.status == AdminAccountStatus.ACTIVE
            StatusChip(
                text = if (isActive) {
                    stringResource(R.string.status_active)
                } else {
                    stringResource(R.string.status_inactive)
                },
                color = if (isActive) SmlSuccess else SmlTextMuted
            )
        }
    }
}

/**
 * Role enum → localized string.
 */
@Composable
private fun roleLabel(role: AdminRole): String = when (role) {
    AdminRole.SUPER_ADMIN -> stringResource(R.string.admin_role_super)
    AdminRole.MANAGER -> stringResource(R.string.admin_role_manager)
    AdminRole.LIBRARIAN -> stringResource(R.string.admin_role_librarian)
}