package com.sainikmodellibrary.admin.screens

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.data.demoProfile
import com.sainikmodellibrary.admin.ui.components.AdminScreenHeader
import com.sainikmodellibrary.admin.ui.components.ContentCard
import com.sainikmodellibrary.admin.ui.components.DemoDataBanner
import com.sainikmodellibrary.admin.ui.components.DetailRow
import com.sainikmodellibrary.admin.ui.components.SectionHeading
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlSurfaceElevated
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary

/**
 * Sainik Model Library Admin — Profile Screen
 *
 * ⚠️ यह स्क्रीन केवल UI प्रदर्शन है:
 *  - प्रोफ़ाइल डेमो डेटा है (डेमो डेटा फ़ाइल में परिभाषित)।
 *  - कोई वास्तविक authentication, session, या खाता-सुरक्षा नहीं है।
 *  - कोई "edit profile" क्रिया नहीं है, क्योंकि कोई persistence नहीं है।
 */
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AdminScreenHeader(
            title = stringResource(R.string.profile_title),
            subtitle = stringResource(R.string.profile_subtitle),
            onBack = onBack
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoDataBanner(text = stringResource(R.string.demo_only_notice))

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Avatar + Name Card =====
            ContentCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = SmlSurfaceElevated,
                        modifier = Modifier.size(88.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = SmlRed,
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = demoProfile.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = SmlTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = demoProfile.role,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SmlTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Contact information =====
            SectionHeading(text = stringResource(R.string.profile_email))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                DetailRow(
                    label = stringResource(R.string.profile_email),
                    value = demoProfile.email
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(4.dp))
                DetailRow(
                    label = stringResource(R.string.profile_phone),
                    value = demoProfile.phone
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Additional profile fields =====
            SectionHeading(text = stringResource(R.string.profile_name))

            Spacer(modifier = Modifier.height(8.dp))

            ContentCard {
                DetailRow(
                    label = stringResource(R.string.profile_name),
                    value = demoProfile.name
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmlDivider()
                Spacer(modifier = Modifier.height(4.dp))
                DetailRow(
                    label = stringResource(R.string.profile_role),
                    value = demoProfile.role
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            DemoDataBanner(text = stringResource(R.string.profile_demo_notice))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}