# Build and Test Guide — Sainik Model Library Admin

**App Version:** 1.0.0
**Target Environment:** AndroidIDE (on Android phone)
**Date:** 2026-10-25

---

## Overview

यह guide आपको बताती है कि **SainikModelLibraryAdmin_V1** प्रोजेक्ट को AndroidIDE पर खोलने, sync करने, build करने, install करने, और test करने के लिए क्या-क्या करना है।

**अगर आप beginner हैं, तो इस guide को ऊपर से नीचे तक क्रम से पढ़ें।** हर step पूरा करें, फिर अगले पर जाएँ।

---

## 1. Prerequisites

### 1.1 AndroidIDE Version

- **न्यूनतम:** AndroidIDE 2.7.0 या नया
- **अनुशंसित:** AndroidIDE 2.8.0+
- पुराना version है तो Play Store / GitHub से update करें

### 1.2 JDK

- **JDK 17** ज़रूरी है (AGP 8.5.0 के लिए)
- AndroidIDE में: **Settings → Build & Run → JDK → JDK 17** चुनें
- JDK 11 या 21 चुना तो बिल्ड फेल होगा

### 1.3 Storage Space

- कम से कम **3 GB free** (Gradle downloads + build outputs)
- पहली बार build में लगभग **1.5 GB** download होगा (Gradle 8.7, dependencies, Compose libraries)

### 1.4 Internet Connection

- पहली बार build के लिए **stable internet** ज़रूरी है
- Gradle और dependencies download होंगी
- एक बार download होने के बाद offline भी build हो सकता है (cache से)

### 1.5 Android Device/Emulator

- **Android 8.0 (API 26) या उससे नया** — physical device recommended
- यही device पर APK install होगा और ऐप चलेगा

---

## 2. Project Structure Verification

Build शुरू करने से पहले सुनिश्चित करें कि सभी फ़ाइलें सही जगह पर हैं:

```

SainikModelLibraryAdmin_V1/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/sainikmodellibrary/admin/
│       │   │   ├── MainActivity.kt
│       │   │   ├── navigation/AdminNavigation.kt
│       │   │   ├── ui/theme/Color.kt
│       │   │   ├── ui/theme/Type.kt
│       │   │   ├── ui/theme/Theme.kt
│       │   │   ├── ui/components/CommonComponents.kt
│       │   │   ├── ui/components/DashboardScreen.kt
│       │   │   ├── screens/  (11 files)
│       │   │   └── data/DemoData.kt
│       │   └── res/
│       │       ├── values/strings.xml
│       │       ├── values/colors.xml
│       │       ├── values/themes.xml
│       │       └── values-hi/strings.xml
│       └── test/java/com/sainikmodellibrary/admin/ProjectSmokeTest.kt
├── docs/
│   ├── BUILD_AND_TEST.md (यह फ़ाइल)
│   └── VERSION_1_SCOPE.md
└── README.md

```

**⚠️ ज़रूरी:** `gradlew` (शेल स्क्रिप्ट) और `gradle-wrapper.jar` (बाइनरी) — ये दोनों **AndroidIDE अपने आप बनाता है** जब आप पहली बार प्रोजेक्ट खोलते हैं या sync करते हैं। इन्हें manually न बनाएँ।

---

## 3. First-Time Setup (एक बार)

### 3.1 प्रोजेक्ट खोलें

1. AndroidIDE ऐप खोलें
2. **Open Project** पर टैप करें
3. ब्राउज़ करके यहाँ जाएँ:
```

/storage/emulated/0/AndroidIDEProjects/SainikModelLibraryAdmin_V1/

```
4. फ़ोल्डर चुनें → **Open**

### 3.2 JDK 17 चुनें

1. **Settings → Build & Run → JDK Version** खोलें
2. **JDK 17** चुनें
3. **Apply** करें

### 3.3 पहला Sync

1. Toolbar में **Sync Project with Gradle Files** icon (हथौड़ा) दबाएँ
2. यह पहली बार में **2-5 मिनट** ले सकता है
3. नीचे build output में आपको दिखेगा:
   - `Downloading https://services.gradle.org/distributions/gradle-8.7-bin.zip`
   - `Configuring project :app`
   - `BUILD SUCCESSFUL in Xs`

### 3.4 अगर Sync फेल हो

नीचे "Troubleshooting" section देखें।

---

## 4. Build the Debug APK

### 4.1 Build Command

**विकल्प A — Menu:**
1. Menu → **Build** → **Build APK (debug)**
2. या Toolbar में "Build" icon

**विकल्प B — Terminal:**
```bash
cd /storage/emulated/0/AndroidIDEProjects/SainikModelLibraryAdmin_V1
./gradlew assembleDebug
```

4.2 Build Time

· पहली बार: 3-7 मिनट (dependencies download के साथ)
· दूसरी बार: 30-90 सेकंड (cache से)
· अगर सिर्फ 1-2 files बदलें: 20-40 सेकंड

4.3 Build Output Location

```
app/build/outputs/apk/debug/app-debug.apk
```

4.4 सफलता के संकेत

Build output में दिखेगा:

```
> Task :app:assembleDebug
BUILD SUCCESSFUL in Xm Ys
```

---

5. Install on Device

5.1 Option 1 — AndroidIDE से सीधे Run

1. Toolbar में Run icon (हरा त्रिकोण) दबाएँ
2. अगर device connected है और USB debugging ON है → सीधे install हो जाएगा

5.2 Option 2 — APK manually install

1. File Manager खोलें
2. यहाँ जाएँ:
   ```
   SainikModelLibraryAdmin_V1/app/build/outputs/apk/debug/
   ```
3. app-debug.apk पर टैप करें
4. "Install" दबाएँ
5. अगर "Unknown sources" warning आए → Settings में जाकर allow करें

5.3 पहली बार ऐप खोलें

1. Home screen / app drawer में Sainik Model Library Admin icon दिखेगा
2. Tap करें
3. अगर सब सही है: dark background + red TopAppBar + Hindi Dashboard
4. अगर crash हो: logcat check करें (नीचे देखें)

---

6. What to Test Manually (Acceptance Checklist)

6.1 Launch Test

☐ ऐप खुलता है बिना crash
☐ Background dark है (सफ़ेद flash नहीं)
☐ TopAppBar में "Sainik Model Library Admin" दिख रहा है
☐ Dashboard पर "स्वागत है, एडमिन" (Hindi default)

6.2 Navigation Test (सबसे ज़रूरी)

☐ Hamburger icon दबाएँ → drawer खुलता है
☐ Drawer में 12 items दिखते हैं
☐ हर item पर tap करें — सभी screen खुलती हैं
☐ Selected item red indicator से दिखता है
☐ किसी screen पर back arrow दबाएँ → Dashboard पर लौटता है
☐ Android system back भी काम करता है

6.3 Screen-by-Screen Test

Dashboard

☐ 4 summary cards (Students / Pending Admissions / Notices / Attendance)
☐ Card tap करने पर respective screen खुलती है
☐ Quick action cards (4) tap काम करते हैं
☐ Recent Activity में 5 items
☐ "Demo data" banner दिख रहा है

Students

☐ 6 students दिखते हैं
☐ Search field में कुछ type करें → list filter होती है
☐ Filter chips (All/Active/Inactive) काम करते हैं
☐ "Add Student" tap → demo notice दिखती है

Admins

☐ 3 admins दिखते हैं
☐ हर card पर role + status chips
☐ "Add Admin" tap → dialog खुलता है, साफ़ बताता है कि account नहीं बनेगा

Admissions

☐ 5 applications दिखते हैं
☐ Filter chips (All/Pending/Approved/Rejected) काम करते हैं
☐ किसी application पर tap → detail dialog खुलता है
☐ Approve/Reject buttons disabled हैं (grey out)

Notices

☐ 4 notices दिखते हैं
☐ Tap करने पर detail dialog
☐ "Create Notice" tap → form खुलता है
☐ Save Draft / Publish दबाने पर "demo only" confirmation दिखता है

E-books

☐ 4 e-books दिखते हैं
☐ "Add E-book" tap → info dialog (backend चाहिए)
☐ Remove tap → info dialog

Attendance

☐ 3 summary cards (Present/Absent/Late counts)
☐ 5 records दिखते हैं
☐ QR और GPS notices अलग-अलग दिखते हैं

Devices

☐ 3 devices (Approved/Pending/Blocked)
☐ Policy placeholder banner
☐ "Register Device" tap → info dialog

Website Content

☐ 4 sections (Home/About/Contact/Gallery)
☐ किसी section पर tap → edit dialog + live preview
☐ Save Draft/Publish दबाने पर demo confirmation

Settings

☐ Language chips (हिन्दी / English)
☐ English chip दबाएँ → पूरी ऐप English में
☐ वापस हिन्दी chip → पूरी ऐप Hindi में
☐ App version दिख रहा है (1.0.0)
☐ Theme "Dark" दिख रहा है

Profile

☐ Avatar + नाम + भूमिका
☐ ईमेल और फ़ोन
☐ Demo notice दिख रहा है

Help & About

☐ App name + version
☐ Version 1 scope details
☐ Support information

6.4 Language Switching Test (सबसे ज़रूरी feature)

☐ Settings में English chip दबाएँ → ऐप English में
☐ Drawer के सारे labels अंग्रेज़ी में
☐ सभी screen के headings अंग्रेज़ी में
☐ वापस Hindi chip दबाएँ → सब हिंदी में

6.5 Permissions Test

☐ Settings → Apps → Sainik Model Library Admin → Permissions
☐ कोई भी permission नहीं होनी चाहिए (यहाँ कोई entry नहीं दिखेगी)

6.6 No Network Test

☐ Airplane mode ON करें
☐ ऐप पूरी तरह काम करता है (कोई network call नहीं)

---

7. Run Unit Tests

7.1 Gradle Command से

```bash
cd /storage/emulated/0/AndroidIDEProjects/SainikModelLibraryAdmin_V1
./gradlew test
```

7.2 सफलता के संकेत

· BUILD SUCCESSFUL
· ProjectSmokeTest के 13 tests पास होते हैं

7.3 Test Report Location

```
app/build/reports/tests/testDebugUnitTest/index.html
```

AndroidIDE browser में खोलकर देख सकते हैं।

7.4 अगर test फेल हो

· Test output में देखें कौन-सा test फेल हुआ
· data/DemoData.kt में संबंधित data चेक करें
· अगर problem समझ न आए → error message भेजें

---

8. Troubleshooting

8.1 Sync फेल हो जाता है

Error: Could not resolve all dependencies

कारण: Internet या Maven repository नहीं पहुँच पा रहा

समाधान:

1. Internet check करें
2. File → Invalidate Caches / Restart करें
3. फिर sync करें
4. अगर VPN है → disable करें

Error: Unsupported class file major version 61

कारण: JDK version mismatch (यह JDK 17 होना चाहिए)

समाधान:

1. Settings → Build & Run → JDK Version → JDK 17 चुनें
2. Project restart करें
3. फिर sync करें

Error: Minimum supported Gradle version is 8.x

कारण: gradle-wrapper.properties में गलत version

समाधान:

· gradle/wrapper/gradle-wrapper.properties खोलें
· सुनिश्चित करें: distributionUrl=...gradle-8.7-bin.zip

Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain

कारण: gradle-wrapper.jar गायब है

समाधान:

1. AndroidIDE बंद करें
2. प्रोजेक्ट फ़ोल्डर में gradle/wrapper/ खोलें
3. देखें gradle-wrapper.jar है या नहीं (यह ~60 KB होनी चाहिए)
4. अगर नहीं है:
   · कोई दूसरा AndroidIDE प्रोजेक्ट खोलें जो चल रहा हो
   · उसके gradle/wrapper/gradle-wrapper.jar को copy करें
   · इस प्रोजेक्ट के gradle/wrapper/ में paste करें

Error: Plugin [id: 'com.android.application'] was not found

कारण: Root build.gradle.kts में plugin version गायब

समाधान:

· Root build.gradle.kts जाँचें:
  ```kotlin
  plugins {
      id("com.android.application") version "8.5.0" apply false
      id("org.jetbrains.kotlin.android") version "1.9.25" apply false
  }
  ```

8.2 Build फेल हो जाता है

Error: Unresolved reference: ...

कारण: कोई फ़ाइल पूरी नहीं है या पाथ गलत है

समाधान:

1. उस specific file को फिर से paste करें
2. Sync करें
3. फिर build करें

Error: Compose Compiler requires Kotlin version X

कारण: Compose Compiler और Kotlin version mismatch

समाधान:

· app/build.gradle.kts में:
  · kotlinCompilerExtensionVersion = "1.5.15"
  · Root में Kotlin: version "1.9.25"
· ये pair compatible हैं

Error: Out of memory (build के दौरान)

कारण: Gradle को ज़्यादा RAM चाहिए

समाधान:

· gradle.properties खोलें
· बदलें: org.gradle.jvmargs=-Xmx2048m
· फिर से build करें

Error: Theme.SainikModelLibraryAdmin not found

कारण: themes.xml नहीं पढ़ा जा रहा

समाधान:

· app/src/main/res/values/themes.xml मौजूद है या नहीं देखें
· उसमें Theme.SainikModelLibraryAdmin परिभाषित है या नहीं

8.3 Runtime crash

Crash: You need to use a Theme.AppCompat theme

कारण: themes.xml में parent गलत है

समाधान:

· themes.xml में:
  ```xml
  <style name="Theme.SainikModelLibraryAdmin" parent="Theme.AppCompat.NoActionBar">
  ```

Crash: No drawer state / NavController not initialized

कारण: AdminNavigation सही से initialized नहीं हुई

समाधान:

· MainActivity.kt जाँचें — setContent { SainikModelLibraryAdminTheme { AdminNavigation() } }

Crash: Cannot cast ... to Activity

कारण: Theme.kt में LocalView.current.context Activity नहीं है

समाधान:

· MainActivity AppCompatActivity से extend होनी चाहिए
· सुनिश्चित करें class MainActivity : AppCompatActivity()

8.4 भाषा switch नहीं हो रही

कारण 1: AppCompatDelegate.setApplicationLocales कॉल नहीं हो रही

समाधान:

· SettingsScreen.kt जाँचें — chip पर setApplicationLocales call हो रही है

कारण 2: values-hi/strings.xml में keys नहीं हैं

समाधान:

· values/strings.xml और values-hi/strings.xml — दोनों में keys match करें

कारण 3: Activity ComponentActivity है, AppCompatActivity नहीं

समाधान:

· MainActivity.kt में import androidx.appcompat.app.AppCompatActivity
· class MainActivity : AppCompatActivity()

8.5 APK install नहीं हो रहा

कारण: "Unknown sources" allow नहीं है

समाधान:

· Settings → Security → Unknown sources → Allow (या Install unknown apps)
· या File Manager से install के समय "Allow from this source" दबाएँ

8.6 ऐप खुलता है लेकिन सफ़ेद screen

कारण: Theme apply नहीं हुई

समाधान:

· AndroidManifest.xml में theme name check करें
· themes.xml में theme exist करे
· ऐप uninstall करके फिर install करें

8.7 हिंदी नहीं दिख रही (English दिख रही है)

कारण: पहले से कोई locale set है (पुरानी app install)

समाधान:

· ऐप uninstall करें
· Cache clear करें
· फिर install करें
· अगर फिर भी English दिखे → Settings में जाकर Hindi chip दबाएँ

---

9. Common Questions

Q: मुझे build में बहुत समय लग रहा है

A: पहली बार Gradle + dependencies download होती हैं। यह 5-10 मिनट ले सकता है। अगली बार 1 मिनट से कम।

Q: क्या मैं offline build कर सकता हूँ?

A: पहली successful build के बाद हाँ, ज़्यादातर मामलों में। Gradle cache में सब होता है।

Q: क्या मैं इसे दूसरे device पर install कर सकता हूँ?

A: हाँ, app-debug.apk को कहीं भी copy करें और install करें।

Q: मैं release APK कैसे बनाऊँ?

A: Version 1 में release signed APK का setup नहीं है। Debug APK काफी है। Release के लिए signing key चाहिए — Version 2 का काम।

Q: Debug APK का आकार कितना है?

A: लगभग 8-15 MB। (मुख्यतः Compose libraries)

Q: क्या ऐप background में चलेगा?

A: केवल foreground में काम करता है। कोई background service, alarm, या job नहीं।

Q: क्या मैं इसे Play Store पर publish कर सकता हूँ?

A: तकनीकी रूप से हाँ, लेकिन signing, store listing, privacy policy जैसी चीज़ें चाहिए। Version 1 को internal use के लिए रखें।

Q: क्या data कहीं save होता है?

A: नहीं। सब demo है, app बंद होते ही सब reset।

Q: क्या INTERNET permission है?

A: नहीं। AndroidManifest.xml में कोई permission नहीं।

---

10. Final Checklist

Version 1 को "complete" मानने से पहले यह सब pass होना चाहिए:

☐ Sync successful
☐ Debug APK build successful
☐ APK install successful
☐ App launch successful (no crash)
☐ All 12 screens navigable
☐ Language switch works (Hindi ↔ English)
☐ Demo banners visible on all screens
☐ No runtime permissions requested
☐ No network calls made
☐ Unit tests pass
☐ App works offline (airplane mode)

---

11. Support

अगर कोई step पूरा नहीं हो रहा:

1. पहले "Troubleshooting" section में अपना error खोजें
2. अगर मिल न जाए → पूरा error message (copy-paste) भेजें
3. साथ में यह जानकारी भी दें:
   · AndroidIDE version
   · JDK version (Settings → Build & Run)
   · Device Android version
   · किस step पर error आया

---

Document End

Happy Building!