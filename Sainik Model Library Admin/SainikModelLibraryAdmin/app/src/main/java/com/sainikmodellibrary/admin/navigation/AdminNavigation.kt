package com.sainikmodellibrary.admin.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sainikmodellibrary.admin.R
import com.sainikmodellibrary.admin.screens.AdmissionsScreen
import com.sainikmodellibrary.admin.screens.AdminsScreen
import com.sainikmodellibrary.admin.screens.AttendanceScreen
import com.sainikmodellibrary.admin.screens.DevicesScreen
import com.sainikmodellibrary.admin.screens.EbooksScreen
import com.sainikmodellibrary.admin.screens.HelpAboutScreen
import com.sainikmodellibrary.admin.screens.NoticesScreen
import com.sainikmodellibrary.admin.screens.ProfileScreen
import com.sainikmodellibrary.admin.screens.SettingsScreen
import com.sainikmodellibrary.admin.screens.StudentsScreen
import com.sainikmodellibrary.admin.screens.WebsiteContentScreen
import com.sainikmodellibrary.admin.ui.components.DashboardScreen
import com.sainikmodellibrary.admin.ui.components.SmlDivider
import com.sainikmodellibrary.admin.ui.theme.SmlBackground
import com.sainikmodellibrary.admin.ui.theme.SmlRed
import com.sainikmodellibrary.admin.ui.theme.SmlRedMuted
import com.sainikmodellibrary.admin.ui.theme.SmlSurface
import com.sainikmodellibrary.admin.ui.theme.SmlTextMuted
import com.sainikmodellibrary.admin.ui.theme.SmlTextPrimary
import kotlinx.coroutines.launch

/**
 * Sainik Model Library Admin — Navigation
 *
 * पूरी ऐप की navigation यहीं से नियंत्रित होती है। इसमें:
 *  - Material 3 ModalNavigationDrawer (drawer / side menu)
 *  - TopAppBar जिसमें hamburger (menu) icon है
 *  - NavHost जिसमें 12 स्क्रीन्स registered हैं
 *
 * ── Navigation व्यवहार ──
 *  - Dashboard ऐप का "home" है (startDestination)।
 *  - Drawer से कोई भी स्क्रीन खोलने पर back stack [Dashboard, X] हो जाता है।
 *  - किसी भी स्क्रीन पर back (या Android system back) → Dashboard।
 *  - Dashboard पर Android system back → ऐप बंद (default behavior)।
 *  - Drawer में selected item स्पष्ट red indicator से दिखता है।
 *
 *  कोई route गलत नहीं होगा, कोई dead button नहीं।
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AdminDrawerContent(
                navController = navController,
                onItemClick = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            containerColor = SmlBackground,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = SmlTextPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.cd_nav_menu),
                                tint = SmlTextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SmlBackground,
                        titleContentColor = SmlTextPrimary,
                        navigationIconContentColor = SmlTextPrimary
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.DASHBOARD,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // ===== Dashboard (start) =====
                composable(Routes.DASHBOARD) {
                    DashboardScreen(
                        onNavigateToStudents = {
                            navigateTo(navController, Routes.STUDENTS)
                        },
                        onNavigateToAdmissions = {
                            navigateTo(navController, Routes.ADMISSIONS)
                        },
                        onNavigateToNotices = {
                            navigateTo(navController, Routes.NOTICES)
                        },
                        onNavigateToAttendance = {
                            navigateTo(navController, Routes.ATTENDANCE)
                        }
                    )
                }

                // ===== Other screens =====
                composable(Routes.STUDENTS) {
                    StudentsScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.ADMINS) {
                    AdminsScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.ADMISSIONS) {
                    AdmissionsScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.NOTICES) {
                    NoticesScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.EBOOKS) {
                    EbooksScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.ATTENDANCE) {
                    AttendanceScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.DEVICES) {
                    DevicesScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.WEBSITE) {
                    WebsiteContentScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.SETTINGS) {
                    SettingsScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.PROFILE) {
                    ProfileScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.HELP_ABOUT) {
                    HelpAboutScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

/**
 * Drawer का अंदरूनी content — brand header + 12 navigation items।
 */
@Composable
private fun AdminDrawerContent(
    navController: NavHostController,
    onItemClick: () -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    ModalDrawerSheet(
        drawerContainerColor = SmlSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            // ===== Brand header =====
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = SmlTextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.version_label) + " 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = SmlTextMuted
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SmlDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))

            // ===== Navigation items =====
            allNavItems().forEach { item ->
                val selected = currentRoute == item.route
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(item.labelRes),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    selected = selected,
                    onClick = {
                        onItemClick()
                        if (currentRoute != item.route) {
                            navigateTo(navController, item.route)
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = SmlRedMuted,
                        selectedIconColor = SmlRed,
                        selectedTextColor = SmlRed,
                        unselectedContainerColor = Color.Transparent,
                        unselectedIconColor = SmlTextMuted,
                        unselectedTextColor = SmlTextPrimary
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ============================================================
// Routes
// ============================================================
private object Routes {
    const val DASHBOARD = "dashboard"
    const val STUDENTS = "students"
    const val ADMINS = "admins"
    const val ADMISSIONS = "admissions"
    const val NOTICES = "notices"
    const val EBOOKS = "ebooks"
    const val ATTENDANCE = "attendance"
    const val DEVICES = "devices"
    const val WEBSITE = "website"
    const val SETTINGS = "settings"
    const val PROFILE = "profile"
    const val HELP_ABOUT = "help_about"
}

// ============================================================
// Nav Item Model
// ============================================================
private data class NavItem(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector
)

@Composable
private fun allNavItems(): List<NavItem> = listOf(
    NavItem(Routes.DASHBOARD, R.string.nav_dashboard, Icons.Default.Dashboard),
    NavItem(Routes.STUDENTS, R.string.nav_students, Icons.Default.People),
    NavItem(Routes.ADMINS, R.string.nav_admins, Icons.Default.AdminPanelSettings),
    NavItem(Routes.ADMISSIONS, R.string.nav_admissions, Icons.Default.Description),
    NavItem(Routes.NOTICES, R.string.nav_notices, Icons.Default.Notifications),
    NavItem(Routes.EBOOKS, R.string.nav_ebooks, Icons.Default.LibraryBooks),
    NavItem(Routes.ATTENDANCE, R.string.nav_attendance, Icons.Default.CheckCircle),
    NavItem(Routes.DEVICES, R.string.nav_devices, Icons.Default.PhoneAndroid),
    NavItem(Routes.WEBSITE, R.string.nav_website, Icons.Default.Language),
    NavItem(Routes.SETTINGS, R.string.nav_settings, Icons.Default.Settings),
    NavItem(Routes.PROFILE, R.string.nav_profile, Icons.Default.Person),
    NavItem(Routes.HELP_ABOUT, R.string.nav_help_about, Icons.Default.Info)
)

// ============================================================
// Navigation helper
// ============================================================
/**
 * Drawer/Quick-action से किसी भी स्क्रीन पर navigation।
 *
 * `launchSingleTop` — वही स्क्रीन दोबारा नहीं जुड़ेगी।
 * `popUpTo(Dashboard)` — back stack हमेशा [Dashboard, X] होगी, ताकि
 * back पर सीधे Dashboard मिले और deep stack न बने।
 */
private fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(Routes.DASHBOARD) { inclusive = false }
    }
}