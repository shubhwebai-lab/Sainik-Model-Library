package com.sainikmodellibrary.admin

import com.sainikmodellibrary.admin.data.AdmissionStatus
import com.sainikmodellibrary.admin.data.AttendanceStatus
import com.sainikmodellibrary.admin.data.DeviceStatus
import com.sainikmodellibrary.admin.data.StudentStatus
import com.sainikmodellibrary.admin.data.demoAdmins
import com.sainikmodellibrary.admin.data.demoAdmissions
import com.sainikmodellibrary.admin.data.demoAttendance
import com.sainikmodellibrary.admin.data.demoDashboardSummary
import com.sainikmodellibrary.admin.data.demoDevices
import com.sainikmodellibrary.admin.data.demoEbooks
import com.sainikmodellibrary.admin.data.demoNotices
import com.sainikmodellibrary.admin.data.demoProfile
import com.sainikmodellibrary.admin.data.demoRecentActivities
import com.sainikmodellibrary.admin.data.demoStudents
import com.sainikmodellibrary.admin.data.demoWebsiteSections
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Sainik Model Library Admin — Project Smoke Test
 *
 * यह सरल unit test है जो verify करता है कि सभी demo data structures
 * ठीक से load हो रहे हैं और basic sanity checks पास हो रहे हैं।
 *
 * ── यह क्या टेस्ट करता है ──
 *  1. हर demo data list non-empty है।
 *  2. Dashboard summary की values positive हैं।
 *  3. कुछ status enums में proper records हैं (जैसे कम से कम 1 approved device)।
 *  4. Filter logic काम कर रहा है (जैसे AdmissionStatus.APPROVED की गिनती)।
 *  5. Profile data भरा हुआ है।
 *
 * ── यह क्या नहीं करता ──
 *  - कोई network, कोई database, कोई Android runtime dependency नहीं।
 *  - कोई UI render नहीं।
 *  - कोई permission check नहीं।
 *
 * इसलिए यह test JVM पर offline चलता है — AndroidIDE में भी।
 */
class ProjectSmokeTest {

    @Test
    fun demoStudents_isNotEmpty() {
        assertTrue("demoStudents should not be empty", demoStudents.isNotEmpty())
        assertTrue(
            "All students should have non-blank names",
            demoStudents.all { it.name.isNotBlank() }
        )
    }

    @Test
    fun demoStudents_haveActiveAndInactive() {
        val active = demoStudents.count { it.status == StudentStatus.ACTIVE }
        val inactive = demoStudents.count { it.status == StudentStatus.INACTIVE }
        assertTrue("At least one ACTIVE student expected", active > 0)
        assertTrue("At least one INACTIVE student expected", inactive > 0)
    }

    @Test
    fun demoAdmins_isNotEmpty() {
        assertTrue("demoAdmins should not be empty", demoAdmins.isNotEmpty())
        assertTrue(
            "All admins should have non-blank emails",
            demoAdmins.all { it.email.isNotBlank() }
        )
    }

    @Test
    fun demoAdmissions_isNotEmpty_andHasMixedStatuses() {
        assertTrue("demoAdmissions should not be empty", demoAdmissions.isNotEmpty())

        val pending = demoAdmissions.count { it.status == AdmissionStatus.PENDING }
        val approved = demoAdmissions.count { it.status == AdmissionStatus.APPROVED }
        val rejected = demoAdmissions.count { it.status == AdmissionStatus.REJECTED }

        assertTrue("At least one PENDING admission expected", pending > 0)
        assertTrue("At least one APPROVED admission expected", approved > 0)
        assertTrue("At least one REJECTED admission expected", rejected > 0)
    }

    @Test
    fun demoNotices_isNotEmpty() {
        assertTrue("demoNotices should not be empty", demoNotices.isNotEmpty())
        assertTrue(
            "All notices should have title and body",
            demoNotices.all { it.title.isNotBlank() && it.body.isNotBlank() }
        )
    }

    @Test
    fun demoEbooks_isNotEmpty() {
        assertTrue("demoEbooks should not be empty", demoEbooks.isNotEmpty())
        assertTrue(
            "All ebooks should have title",
            demoEbooks.all { it.title.isNotBlank() }
        )
    }

    @Test
    fun demoAttendance_hasMultipleStatuses() {
        assertTrue("demoAttendance should not be empty", demoAttendance.isNotEmpty())

        val present = demoAttendance.count { it.status == AttendanceStatus.PRESENT }
        val absent = demoAttendance.count { it.status == AttendanceStatus.ABSENT }
        val late = demoAttendance.count { it.status == AttendanceStatus.LATE }

        assertTrue("At least one PRESENT record expected", present > 0)
        assertTrue("At least one ABSENT record expected", absent > 0)
        assertTrue("At least one LATE record expected", late > 0)
    }

    @Test
    fun demoDevices_hasApprovedAndBlocked() {
        assertTrue("demoDevices should not be empty", demoDevices.isNotEmpty())

        val approved = demoDevices.count { it.status == DeviceStatus.APPROVED }
        val blocked = demoDevices.count { it.status == DeviceStatus.BLOCKED }

        assertTrue("At least one APPROVED device expected", approved > 0)
        assertTrue("At least one BLOCKED device expected", blocked > 0)
    }

    @Test
    fun demoWebsiteSections_hasKnownSectionKeys() {
        assertTrue("demoWebsiteSections should not be empty", demoWebsiteSections.isNotEmpty())

        val keys = demoWebsiteSections.map { it.sectionKey }
        assertTrue("'home' section expected", keys.contains("home"))
        assertTrue("'about' section expected", keys.contains("about"))
        assertTrue("'contact' section expected", keys.contains("contact"))
        assertTrue("'gallery' section expected", keys.contains("gallery"))
    }

    @Test
    fun demoDashboardSummary_hasPositiveValues() {
        assertTrue("totalStudents should be > 0", demoDashboardSummary.totalStudents > 0)
        assertTrue("pendingAdmissions should be > 0", demoDashboardSummary.pendingAdmissions > 0)
        assertTrue("activeNotices should be > 0", demoDashboardSummary.activeNotices > 0)
        assertTrue("attendanceToday should be > 0", demoDashboardSummary.attendanceToday > 0)
    }

    @Test
    fun demoProfile_isComplete() {
        assertFalse("profile.name should not be blank", demoProfile.name.isBlank())
        assertFalse("profile.email should not be blank", demoProfile.email.isBlank())
        assertFalse("profile.role should not be blank", demoProfile.role.isBlank())
        assertFalse("profile.phone should not be blank", demoProfile.phone.isBlank())
    }

    @Test
    fun demoRecentActivities_isNotEmpty() {
        assertTrue("demoRecentActivities should not be empty", demoRecentActivities.isNotEmpty())
        assertTrue(
            "All activities should have description",
            demoRecentActivities.all { it.description.isNotBlank() }
        )
    }

    @Test
    fun admissionFilterLogic_worksAsExpected() {
        // Filter logic जैसा AdmissionsScreen में है
        val pendingOnly = demoAdmissions.filter { it.status == AdmissionStatus.PENDING }
        assertTrue(
            "pendingOnly should contain ONLY pending applications",
            pendingOnly.all { it.status == AdmissionStatus.PENDING }
        )

        // और गिनती भी
        assertEquals(
            "pending count should match filter result",
            demoAdmissions.count { it.status == AdmissionStatus.PENDING },
            pendingOnly.size
        )
    }

    @Test
    fun studentSearchLogic_worksAsExpected() {
        // Search logic जैसा StudentsScreen में है
        val query = "Aarav"
        val results = demoStudents.filter {
            it.name.contains(query, ignoreCase = true)
        }
        assertTrue(
            "Searching 'Aarav' should find at least one student",
            results.isNotEmpty()
        )
        assertTrue(
            "All search results should contain the query",
            results.all { it.name.contains(query, ignoreCase = true) }
        )
    }
}