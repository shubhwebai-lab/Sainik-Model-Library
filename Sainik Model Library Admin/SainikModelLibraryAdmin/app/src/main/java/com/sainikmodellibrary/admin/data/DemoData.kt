package com.sainikmodellibrary.admin.data

/**
 * Sainik Model Library Admin — Version 1 Demo Data
 *
 * ⚠️ महत्वपूर्ण सूचना:
 * यह फ़ाइल केवल प्रदर्शन (demonstration) डेटा रखती है।
 * कोई वास्तविक छात्र, एडमिन, आवेदन, उपस्थिति या डिवाइस रिकॉर्ड नहीं है।
 * कोई डेटाबेस, नेटवर्क या persistence यहाँ उपयोग नहीं होती।
 *
 * नियम:
 *  - Status (Active/Pending/etc.) के लिए enum का उपयोग — UI में string resources
 *    से स्थानीयकृत (localized) किया जाएगा।
 *  - व्यक्ति/विषय के नाम, नोटिस शीर्षक आदि को "डेटा" मानें — ये अनुवाद योग्य नहीं,
 *    क्योंकि वास्तविक ऐप में ये उपयोगकर्ता द्वारा दर्ज किए गए रिकॉर्ड होते हैं।
 */

// ===== Status Enums (UI में localized किए जाएँगे) =====

enum class StudentStatus { ACTIVE, INACTIVE }
enum class AdminRole { SUPER_ADMIN, MANAGER, LIBRARIAN }
enum class AdminAccountStatus { ACTIVE, INACTIVE }
enum class AdmissionStatus { PENDING, APPROVED, REJECTED }
enum class NoticePriority { NORMAL, HIGH }
enum class AttendanceStatus { PRESENT, ABSENT, LATE }
enum class DeviceStatus { APPROVED, PENDING, BLOCKED }

// ===== Data Models =====

data class Student(
    val id: String,
    val name: String,
    val className: String,
    val rollNumber: String,
    val phone: String,
    val status: StudentStatus,
    val addedOn: String
)

data class AdminUser(
    val id: String,
    val name: String,
    val email: String,
    val role: AdminRole,
    val status: AdminAccountStatus
)

data class AdmissionApplication(
    val id: String,
    val applicantName: String,
    val appliedClass: String,
    val submittedOn: String,
    val status: AdmissionStatus
)

data class Notice(
    val id: String,
    val title: String,
    val body: String,
    val publishDate: String,
    val priority: NoticePriority
)

data class Ebook(
    val id: String,
    val title: String,
    val size: String,
    val uploadedOn: String
)

data class AttendanceRecord(
    val id: String,
    val studentName: String,
    val className: String,
    val checkIn: String,
    val checkOut: String,
    val status: AttendanceStatus
)

data class DeviceInfo(
    val id: String,
    val deviceName: String,
    val model: String,
    val status: DeviceStatus,
    val lastActive: String
)

data class WebsiteSection(
    val id: String,
    val sectionKey: String,
    val title: String,
    val content: String
)

data class AdminProfile(
    val name: String,
    val role: String,
    val email: String,
    val phone: String
)

data class RecentActivity(
    val id: String,
    val description: String,
    val timestamp: String
)

data class DashboardSummary(
    val totalStudents: Int,
    val pendingAdmissions: Int,
    val activeNotices: Int,
    val attendanceToday: Int
)

// ===== Demo Data Lists =====

val demoStudents: List<Student> = listOf(
    Student("S001", "Aarav Sharma", "Class 8-A", "14", "+91 98765 43210", StudentStatus.ACTIVE, "12 Oct 2026"),
    Student("S002", "Priya Verma", "Class 9-B", "22", "+91 98765 43211", StudentStatus.ACTIVE, "10 Oct 2026"),
    Student("S003", "Rohan Gupta", "Class 10-A", "07", "+91 98765 43212", StudentStatus.INACTIVE, "05 Oct 2026"),
    Student("S004", "Sneha Singh", "Class 7-C", "33", "+91 98765 43213", StudentStatus.ACTIVE, "01 Oct 2026"),
    Student("S005", "Kabir Khan", "Class 11-A", "05", "+91 98765 43214", StudentStatus.ACTIVE, "28 Sep 2026"),
    Student("S006", "Anaya Reddy", "Class 12-B", "19", "+91 98765 43215", StudentStatus.INACTIVE, "20 Sep 2026")
)

val demoAdmins: List<AdminUser> = listOf(
    AdminUser("A001", "Vikram Rathore", "vikram.rathore@sainikmodel.edu.in", AdminRole.SUPER_ADMIN, AdminAccountStatus.ACTIVE),
    AdminUser("A002", "Neha Kapoor", "neha.kapoor@sainikmodel.edu.in", AdminRole.LIBRARIAN, AdminAccountStatus.ACTIVE),
    AdminUser("A003", "Suresh Menon", "suresh.menon@sainikmodel.edu.in", AdminRole.MANAGER, AdminAccountStatus.INACTIVE)
)

val demoAdmissions: List<AdmissionApplication> = listOf(
    AdmissionApplication("AD001", "Aditya Joshi", "Class 8-A", "18 Oct 2026", AdmissionStatus.PENDING),
    AdmissionApplication("AD002", "Meera Nair", "Class 9-B", "17 Oct 2026", AdmissionStatus.PENDING),
    AdmissionApplication("AD003", "Arjun Patel", "Class 7-C", "15 Oct 2026", AdmissionStatus.APPROVED),
    AdmissionApplication("AD004", "Diya Sharma", "Class 10-A", "14 Oct 2026", AdmissionStatus.REJECTED),
    AdmissionApplication("AD005", "Ishaan Bose", "Class 11-A", "12 Oct 2026", AdmissionStatus.APPROVED)
)

val demoNotices: List<Notice> = listOf(
    Notice(
        "N001",
        "Library Closed on Diwali",
        "The library will remain closed on 20 October on account of Diwali. Regular hours resume on 21 October.",
        "15 Oct 2026",
        NoticePriority.HIGH
    ),
    Notice(
        "N002",
        "New Arrivals: Science Section",
        "Forty new titles have been added to the science section. Members are encouraged to explore the updated collection.",
        "12 Oct 2026",
        NoticePriority.NORMAL
    ),
    Notice(
        "N003",
        "Annual Book Fair 2026",
        "The annual book fair will be held in the main hall from 25 to 28 October. All students and staff are welcome.",
        "10 Oct 2026",
        NoticePriority.HIGH
    ),
    Notice(
        "N004",
        "Quiet Hours Reminder",
        "Please maintain silence during quiet hours between 2 PM and 4 PM in the reading area.",
        "05 Oct 2026",
        NoticePriority.NORMAL
    )
)

val demoEbooks: List<Ebook> = listOf(
    Ebook("E001", "Introduction to Physics", "4.2 MB", "12 Oct 2026"),
    Ebook("E002", "Indian History: A Brief Survey", "6.8 MB", "08 Oct 2026"),
    Ebook("E003", "Mathematics for Class 8", "3.5 MB", "01 Oct 2026"),
    Ebook("E004", "English Literature Reader", "5.1 MB", "25 Sep 2026")
)

val demoAttendance: List<AttendanceRecord> = listOf(
    AttendanceRecord("AT001", "Aarav Sharma", "Class 8-A", "08:45 AM", "04:30 PM", AttendanceStatus.PRESENT),
    AttendanceRecord("AT002", "Priya Verma", "Class 9-B", "09:02 AM", "04:35 PM", AttendanceStatus.PRESENT),
    AttendanceRecord("AT003", "Rohan Gupta", "Class 10-A", "09:30 AM", "04:15 PM", AttendanceStatus.LATE),
    AttendanceRecord("AT004", "Sneha Singh", "Class 7-C", "—", "—", AttendanceStatus.ABSENT),
    AttendanceRecord("AT005", "Kabir Khan", "Class 11-A", "08:50 AM", "04:20 PM", AttendanceStatus.PRESENT)
)

val demoDevices: List<DeviceInfo> = listOf(
    DeviceInfo("D001", "Redmi Note 12", "Redmi Note 12", DeviceStatus.APPROVED, "25 Oct 2026"),
    DeviceInfo("D002", "Samsung A54", "Samsung A54", DeviceStatus.APPROVED, "20 Oct 2026"),
    DeviceInfo("D003", "Vivo Y21", "Vivo Y21", DeviceStatus.BLOCKED, "10 Oct 2026")
)

val demoWebsiteSections: List<WebsiteSection> = listOf(
    WebsiteSection(
        "W001",
        "home",
        "Home",
        "Welcome to Sainik Model Library. Explore our vast collection of books, ebooks, and educational resources."
    ),
    WebsiteSection(
        "W002",
        "about",
        "About",
        "The Sainik Model Library has served students and staff for over two decades with a commitment to knowledge."
    ),
    WebsiteSection(
        "W003",
        "contact",
        "Contact",
        "Email: info@sainikmodel.edu.in | Phone: +91 98765 43210"
    ),
    WebsiteSection(
        "W004",
        "gallery",
        "Gallery",
        "A curated selection of photographs from events, book fairs, and reading sessions."
    )
)

val demoProfile: AdminProfile = AdminProfile(
    name = "Vikram Rathore",
    role = "Super Admin",
    email = "vikram.rathore@sainikmodel.edu.in",
    phone = "+91 98765 43210"
)

val demoRecentActivities: List<RecentActivity> = listOf(
    RecentActivity("R001", "New admission application received from Aditya Joshi", "Today, 10:30 AM"),
    RecentActivity("R002", "Notice published: Library Closed on Diwali", "Today, 09:15 AM"),
    RecentActivity("R003", "New student added: Aarav Sharma", "Yesterday, 04:20 PM"),
    RecentActivity("R004", "E-book uploaded: Introduction to Physics", "Yesterday, 11:45 AM"),
    RecentActivity("R005", "Device registered: Redmi Note 12", "2 days ago")
)

val demoDashboardSummary: DashboardSummary = DashboardSummary(
    totalStudents = 248,
    pendingAdmissions = 12,
    activeNotices = 6,
    attendanceToday = 214
)