# Sainik Model Library Admin

**Version:** 1.0.0 (Version Code: 1)
**Package:** `com.sainikmodellibrary.admin`
**Platform:** Android (Kotlin + Jetpack Compose)
**Min SDK:** 26 (Android 8.0)

---

## Overview

Sainik Model Library Admin एक **UI-only प्रीव्यू** है — Version 1 में कोई backend, database, authentication, या real data operation लागू नहीं है। यह ऐप library administrators के लिए एक प्रीमियम, dark-themed, 12-स्क्रीन का interface प्रदान करता है, जिसे भविष्य में असली backend के साथ जोड़ा जा सके।

**Default language:** हिंदी
**Supported languages:** हिंदी, English (Settings में switch करने योग्य)

---

## Screens

1. **Dashboard** — Welcome, 4 summary cards, quick actions, recent activity
2. **Students** — Search, filter, और student cards
3. **Admins** — Role + status के साथ admin list
4. **Admission Applications** — Filter chips + detail dialog (Approve/Reject disabled)
5. **Notices** — Notice cards, detail view, create form (UI-only)
6. **E-books** — E-book list, upload/remove placeholders
7. **Attendance** — Summary + records, QR/GPS notices
8. **Devices** — Device list, 2-device policy placeholder
9. **Website Content** — Section editing with live preview
10. **Settings** — Working language switch (Hindi ↔ English)
11. **Profile** — Admin profile (demo data)
12. **Help & About** — App info, Version 1 scope, support info

सभी स्क्रीन्स एक Material 3 **navigation drawer** से सुलभ हैं।

---

## Design System

| Role | Hex | Usage |
|---|---|---|
| Primary Red | `#D72638` | Brand, buttons, accents |
| Background | `#101114` | App background |
| Surface | `#1B1D22` | Cards, panels |
| Elevated Surface | `#24272D` | Higher-emphasis surfaces |
| Text Primary | `#FFFFFF` | Main text |
| Text Muted | `#9CA3AF` | Secondary text |
| Success | `#31B779` | Present/Approved/Active states |
| Warning | `#FFB547` | Pending/Late/High-priority |

**Theme:** हमेशा dark। कोई light mode नहीं।

---

## Tech Stack

- **Language:** Kotlin 1.9.25
- **UI:** Jetpack Compose (Material 3)
- **Gradle:** 8.7
- **AGP:** 8.5.0
- **Compose Compiler:** 1.5.15
- **JDK:** 17
- **IDE:** AndroidIDE (on Android phone)
- **Navigation:** Navigation Compose 2.7.7

पूरी version compatibility की जानकारी के लिए [`docs/BUILD_AND_TEST.md`](docs/BUILD_AND_TEST.md) देखें।

---

## Project Structure

```

SainikModelLibraryAdmin_V1/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradle/wrapper/gradle-wrapper.properties
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/sainikmodellibrary/admin/
│       │   │   ├── MainActivity.kt
│       │   │   ├── navigation/AdminNavigation.kt
│       │   │   ├── ui/theme/{Color, Type, Theme}.kt
│       │   │   ├── ui/components/{CommonComponents, DashboardScreen}.kt
│       │   │   ├── screens/  (11 screen files)
│       │   │   └── data/DemoData.kt
│       │   └── res/
│       │       ├── values/{strings, colors, themes}.xml
│       │       └── values-hi/strings.xml
│       └── test/java/com/sainikmodellibrary/admin/ProjectSmokeTest.kt
├── docs/
│   ├── BUILD_AND_TEST.md
│   └── VERSION_1_SCOPE.md
└── README.md (यह फ़ाइल)

```

---

## What Version 1 Does NOT Do

Version 1 स्पष्ट रूप से **यह सब नहीं करता**:

- ❌ कोई वास्तविक student, admin, admission, notice, ebook, attendance, या device record **सहेजा नहीं जाता**
- ❌ कोई backend API call नहीं
- ❌ कोई database नहीं
- ❌ कोई authentication या authorization नहीं
- ❌ कोई file upload/download नहीं
- ❌ QR-based या GPS-based attendance नहीं
- ❌ कोई live website publishing नहीं
- ❌ भाषा-चयन को छोड़कर कोई persistent preference नहीं
- ❌ कोई dangerous permissions नहीं (कोई भी permission नहीं माँगी जाती)

**हर screen पर "Demo data" banner स्पष्ट रूप से दिखता है।**

---

## Getting Started (AndroidIDE पर)

पूरी build और test प्रक्रिया के लिए [`docs/BUILD_AND_TEST.md`](docs/BUILD_AND_TEST.md) देखें।

**Quick start:**

1. AndroidIDE में `SainikModelLibraryAdmin_V1/` फ़ोल्डर खोलें।
2. **Sync Project with Gradle Files** दबाएँ (पहली बार Gradle 8.7 download होगा, 2-5 मिनट)।
3. **Build → Build APK (debug)** चुनें।
4. APK install करें और ऐप चलाएँ।
5. भाषा बदलने के लिए: **Settings → Language → English/Hindi**।

---

## Known Limitations

- **Language persistence:** भाषा-चयन इस session में लागू रहता है। ऐप restart पर default हिंदी लौट सकता है। (AppCompat per-app locale persistence के लिए `androidx.appcompat.app.AppLocalesMetadataHolderService` Manifest entry चाहिए — Version 2 में जोड़ा जा सकता है।)
- **Data:** सभी data `data/DemoData.kt` में hardcoded है।
- **No networking:** कोई network permission, कोई INTERNET permission नहीं।
- **Icons:** Android system default icon (`@android:drawable/sym_def_app_icon`) उपयोग हो रहा है। Custom launcher icon Version 2 में जोड़ेंगे।

---

## Version History

| Version | Date | Notes |
|---|---|---|
| 1.0.0 | 2026-10-25 | Initial UI-only preview, 12 screens, Hindi/English |

---

## License & Usage

यह एक private admin tool है। कोई public license नहीं है। Sainik Model Library के internal उपयोग के लिए।

---

## Contact

सहायता के लिए अपने library administrator से संपर्क करें।