package com.sainikmodellibrary.admin.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Sainik Model Library Admin — Design System Colors
 *
 * यहाँ हम केवल 7 मुख्य रंग और उनके कुछ हल्के वेरिएंट रख रहे हैं।
 * सभी रंग [values/colors.xml] में भी परिभाषित हैं, ताकि system theme (status bar, etc.)
 * और Compose theme — दोनों एक ही रंग-पैलेट से चलें।
 *
 * नियम: किसी भी UI स्क्रीन में रंग सीधे हार्डकोड न करें — इन constants का उपयोग करें।
 */

// ===== Primary Brand =====
val SmlRed: Color = Color(0xFFD72638)

// ===== Backgrounds & Surfaces =====
val SmlBackground: Color = Color(0xFF101114)
val SmlSurface: Color = Color(0xFF1B1D22)
val SmlSurfaceElevated: Color = Color(0xFF24272D)

// ===== Text =====
val SmlTextPrimary: Color = Color(0xFFFFFFFF)
val SmlTextMuted: Color = Color(0xFF9CA3AF)

// ===== Status Colors =====
val SmlSuccess: Color = Color(0xFF31B779)
val SmlWarning: Color = Color(0xFFFFB547)

// ===== Derived / Helper Colors (palette से बने) =====

// Divider / border — muted text को बहुत कम opacity पर
val SmlDivider: Color = Color(0xFF2A2D33)

// Red का हल्का ट्रांसपेरेंट वर्ज़न — chip backgrounds आदि के लिए
val SmlRedMuted: Color = SmlRed.copy(alpha = 0.15f)

// Success / Warning के ट्रांसपेरेंट बैकग्राउंड — status chips के लिए
val SmlSuccessMuted: Color = SmlSuccess.copy(alpha = 0.15f)
val SmlWarningMuted: Color = SmlWarning.copy(alpha = 0.15f)

// Disabled state
val SmlDisabled: Color = Color(0xFF3A3D44)
val SmlOnDisabled: Color = Color(0xFF6B7280)