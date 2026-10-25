# Version 1 Scope — Sainik Model Library Admin

**Document Version:** 1.0
**App Version:** 1.0.0
**Date:** 2026-10-25
**Status:** UI-only preview

---

## Purpose of This Document

यह दस्तावेज़ स्पष्ट रूप से तय करता है कि **Version 1 में क्या शामिल है और क्या शामिल नहीं है**। यह developers, testers और stakeholders — तीनों के लिए एक ही स्रोत (single source of truth) है ताकि कोई भ्रम न रहे।

**TL;DR:** Version 1 एक पूरी तरह काम करने वाला, प्रीमियम-looking, 12-स्क्रीन का Android UI है — लेकिन **कोई real data, backend, database, authentication, या file operation नहीं है**।

---

## 1. What IS Included in Version 1

### 1.1 Screens (12 total)

| # | Screen | Status | Notes |
|---|---|---|---|
| 1 | Dashboard | ✅ Complete | Summary cards + quick actions + recent activity |
| 2 | Students | ✅ Complete | Search + filter + cards (local state only) |
| 3 | Admins | ✅ Complete | Role/status chips + Add dialog (demo) |
| 4 | Admission Applications | ✅ Complete | Filter chips + detail dialog (Approve/Reject disabled) |
| 5 | Notices | ✅ Complete | List + detail + create form (demo save/publish) |
| 6 | E-books | ✅ Complete | List + upload/remove placeholders |
| 7 | Attendance | ✅ Complete | Summary + records + QR/GPS notices |
| 8 | Devices | ✅ Complete | List + 2-device policy placeholder |
| 9 | Website Content | ✅ Complete | Section edit + live preview (demo publish) |
| 10 | Settings | ✅ Complete | **Working** language switch (Hindi ↔ English) |
| 11 | Profile | ✅ Complete | Demo profile presentation |
| 12 | Help & About | ✅ Complete | App info + scope + support info |

### 1.2 Navigation

- **Material 3 Modal Navigation Drawer** with 12 items
- Selected item visually indicated (red accent)
- Back navigation always returns to Dashboard
- No dead buttons, no unreachable screens

### 1.3 Theming

- **Full dark theme** with 8-color palette
- System status bar + navigation bar tinted to match background
- Material 3 dynamic-free color scheme (fixed brand palette)
- Custom typography (system sans-serif, tuned sizes)
- Reusable components in `CommonComponents.kt`

### 1.4 Internationalization

- **Hindi (default)** and **English** string resources
- `values/strings.xml` = English fallback
- `values-hi/strings.xml` = Hindi
- **Working in-app language switch** in Settings (uses `AppCompatDelegate.setApplicationLocales`)
- All user-visible strings externalized — no hardcoded text in screens

### 1.5 Demo Data

- All sample data centralized in `data/DemoData.kt`
- Clearly labeled on every screen with "Demo data" banner
- 6 students, 3 admins, 5 admissions, 4 notices, 4 e-books, 5 attendance records, 3 devices, 4 website sections
- Read-only — no persistence

### 1.6 Project Quality

- **Compiles and builds** a debug APK
- **Runs on API 26+** (Android 8.0+)
- **Unit tests** in `ProjectSmokeTest.kt` verify demo data integrity
- **No permissions** requested (not even INTERNET)
- **No crash** in normal navigation flow
- **No memory leaks** in navigation (standard Navigation Compose patterns)

---

## 2. What Is NOT Included in Version 1

### 2.1 No Backend

- ❌ कोई REST API, GraphQL, Firebase, Supabase, या कोई server नहीं
- ❌ कोई network code नहीं
- ❌ कोई `INTERNET` permission नहीं
- ❌ कोई network error handling नहीं (क्योंकि कोई network नहीं)

### 2.2 No Database

- ❌ कोई Room, SQLite, DataStore, SharedPreferences, या file storage नहीं
- ❌ सभी data `DemoData.kt` में hardcoded
- ❌ App restart पर कोई भी बदलाव नहीं बचता
- ❌ भाषा-चयन को छोड़कर कोई persistent preference नहीं

### 2.3 No Authentication

- ❌ कोई login screen नहीं
- ❌ कोई signup screen नहीं
- ❌ कोई session, token, cookie, या password नहीं
- ❌ Profile screen demo data है — कोई real user account नहीं
- ❌ Admins screen में "Add Admin" केवल UI है — कोई account create नहीं होता

### 2.4 No File Operations

- ❌ कोई PDF upload नहीं
- ❌ कोई PDF download नहीं
- ❌ कोई file storage permission नहीं
- ❌ E-books screen पर upload/remove केवल info dialogs हैं

### 2.5 No Real Mutations

- ❌ कोई student add/edit/delete नहीं होता
- ❌ कोई admission approve/reject नहीं होता
- ❌ कोई notice save/publish नहीं होता
- ❌ कोई website content update नहीं होता
- ❌ कोई device register/approve/block नहीं होता
- ❌ सभी "Save", "Publish", "Approve", "Reject" actions या तो **disabled** हैं या **info dialog** दिखाते हैं

### 2.6 No Advanced Features

- ❌ QR-code scanning नहीं (attendance)
- ❌ GPS / location tracking नहीं (attendance)
- ❌ Push notifications नहीं (notices)
- ❌ Camera / photo upload नहीं
- ❌ Biometric authentication नहीं
- ❌ Dark/light theme toggle नहीं (हमेशा dark)

### 2.7 No Persistence

- ❌ Search state reset on screen change
- ❌ Filter state reset on screen change
- ❌ Form inputs lost on dialog close
- ❌ Language choice may reset on app restart (see Known Limitations in README)

---

## 3. Why These Restrictions?

Version 1 को **जान-बूझकर** UI-only रखा गया है क्योंकि:

1. **Honesty:** UI कभी नहीं दावा करता कि कुछ "save" या "publish" हुआ जब वास्तव में नहीं हुआ।
2. **Safety:** बिना backend के कोई data leak, कोई privacy issue, कोई server cost नहीं।
3. **Foundation:** यह UI अगले versions में backend जोड़ने का solid आधार है।
4. **Budget:** ₹0 — कोई third-party paid service नहीं।
5. **Clarity:** Stakeholders पहले UI approve कर सकते हैं, फिर backend चर्चा हो सकती है।

---

## 4. Every Screen's Honest State

हर screen स्पष्ट रूप से अपनी सीमा बताती है। यहाँ वह टेक्स्ट है जो user देखेगा:

### Dashboard
> "यह स्क्रीन केवल प्रदर्शन के लिए डेटा का उपयोग करती है। कोई वास्तविक रिकॉर्ड संग्रहीत या संशोधित नहीं किया जाता।"

### Students
> "Add Student" बटन एक demo notice दिखाता है। कोई student वास्तव में add नहीं होता।

### Admins
> "यह फ़ॉर्म केवल UI प्रदर्शन है। कोई वास्तविक एडमिन खाता नहीं बनाया जाएगा।"

### Admission Applications
> "Version 1 में स्वीकृत और अस्वीकृत बटन केवल प्रदर्शन के लिए हैं। कोई आवेदन वास्तव में स्वीकृत, अस्वीकृत या सर्वर पर नहीं भेजा जाएगा।"

### Notices
> "Version 1 में सहेजें और प्रकाशित करें क्रियाएँ केवल UI हैं। कोई सूचना वास्तव में सहेजी या प्रकाशित नहीं की जाएगी।"

### E-books
> "वास्तविक फ़ाइल अपलोड, संग्रहण और हटाने के लिए बैकएंड सेवा आवश्यक है। यह Version 1 इंटरफ़ेस कोई फ़ाइल अपलोड या हटाता नहीं है।"

### Attendance
> "उपस्थिति लाइव ट्रैक नहीं हो रही है। दिखाए गए रिकॉर्ड केवल प्रदर्शन डेटा हैं।"
> "Version 1 में QR-आधारित चेक-इन लागू नहीं है।"
> "Version 1 में GPS-आधारित उपस्थिति लागू नहीं है।"

### Devices
> "Version 1 में कोई डिवाइस वास्तव में पंजीकृत, सत्यापित, स्वीकृत या अवरुद्ध नहीं होता।"

### Website Content
> "Version 1 में प्रकाशन किसी लाइव वेबसाइट से जुड़ा नहीं है। परिवर्तन केवल UI हैं और कहीं नहीं भेजे जाते।"

### Settings
> "भाषा चयन इस सत्र पर लागू होता है। Version 1 में स्थायी प्राथमिकताएँ सहेजी नहीं जातीं।"

### Profile
> "यह प्रोफ़ाइल प्रदर्शन डेटा है। Version 1 में कोई प्रमाणीकरण या खाता प्रबंधन उपलब्ध नहीं है।"

### Help & About
> "Version 1 Sainik Model Library Admin का केवल-UI पूर्वावलोकन है। कोई बैकएंड, डेटाबेस, प्रमाणीकरण या वास्तविक डेटा संचालन लागू नहीं है।"

---

## 5. Possible Future Versions (Out of Scope for V1)

यह section केवल **योजना** के लिए है — Version 1 में कुछ भी लागू नहीं है।

### Version 1.1 — Local Persistence
- भाषा-चयन को AppCompat locale service के ज़रिए persist करना
- Theme preference (dark/light) को persist करना
- कोई backend नहीं

### Version 1.5 — Local Data
- Room database जोड़ना
- Add/Edit/Delete students, admins, notices
- Data app restart पर बचे

### Version 2.0 — Backend Integration
- REST API या Firebase
- Login + role-based access (Super Admin / Manager / Librarian)
- Real approve/reject workflow
- Real notice publish
- Real e-book upload (Firebase Storage या S3)
- Real device registration with 2-device policy enforcement
- Push notifications

### Version 2.5 — Advanced
- QR-based attendance (with camera)
- GPS-based attendance (with permission flow)
- Real website content sync
- Analytics dashboard

### Version 3.0 — Multi-tenant
- Multiple libraries under one admin
- Reporting + export

**⚠️ ये सब Version 1 में नहीं हैं।** यह section केवल दिशा दिखाने के लिए है।

---

## 6. Acceptance Criteria for Version 1

Version 1 पूरा माना जाएगा जब:

- [x] AndroidIDE में project sync होता है
- [x] Debug APK सफलतापूर्वक बनता है
- [x] APK Android 8.0+ device पर install होता है
- [x] App launch होता है बिना crash
- [x] सभी 12 screens navigation drawer से खुलती हैं
- [x] हर screen पर "Demo data" notice दिखता है
- [x] Hindi और English दोनों में UI दिखता है
- [x] Settings में language switch काम करता है
- [x] कोई runtime permission नहीं माँगी जाती
- [x] कोई network call नहीं होती
- [x] कोई fake success message नहीं
- [x] Disabled actions स्पष्ट रूप से disabled दिखते हैं
- [x] Unit tests पास होते हैं (`ProjectSmokeTest`)
- [x] README और docs पूरी हैं

---

## 7. Sign-off

| Role | Responsibility | Approval |
|---|---|---|
| Product Owner | Scope definition | ☐ |
| Developer | Implementation | ☐ |
| QA | Acceptance testing | ☐ |

---

**Document End**