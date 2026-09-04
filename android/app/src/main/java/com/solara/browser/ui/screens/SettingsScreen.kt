package com.solara.browser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdUnits
import androidx.compose.material.icons.filled.Appearance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.ui.theme.SolaraColors
import com.solara.browser.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val isDynamicColor by viewModel.isDynamicColor.collectAsState()
    val isCompactMode by viewModel.isCompactMode.collectAsState()
    val isReaderMode by viewModel.isReaderMode.collectAsState()
    val isAdBlocker by viewModel.isAdBlockerEnabled.collectAsState()
    val isTrackerBlocking by viewModel.isTrackerBlocking.collectAsState()
    val isHttpsOnly by viewModel.isHttpsOnly.collectAsState()
    val isBiometricLock by viewModel.isBiometricLock.collectAsState()
    val isClearOnExit by viewModel.isClearOnExit.collectAsState()
    val isSwipeNav by viewModel.isSwipeNavigation.collectAsState()
    val fontSize by viewModel.fontSize.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SolaraColors.SolaraColors.BackgroundGradient)
    ) {
        TopAppBar(
            title = {
                Text("Settings", color = SolaraColors.TextPrimary, fontWeight = FontWeight.W600)
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SolaraColors.TextSecondary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SolaraColors.Midnight),
            modifier = Modifier.statusBarsPadding()
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
        ) {
            item {
                SettingsSection("Appearance")
                SettingsToggle(Icons.Default.Palette, "Dynamic Color", "Wallpaper-based colors", isDynamicColor) { viewModel.setDynamicColor(it) }
                SettingsToggle(Icons.Default.Appearance, "Compact Mode", "Hide toolbar when browsing", isCompactMode) { viewModel.setCompactMode(it) }
                SettingsToggle(Icons.Default.FontDownload, "Reader Mode", "Simplify pages for reading", isReaderMode) { viewModel.setReaderMode(it) }
            }

            item {
                SettingsSection("Privacy & Security")
                SettingsToggle(Icons.Default.AdUnits, "Ad Blocker", "Block advertisements", isAdBlocker) { viewModel.setAdBlocker(it) }
                SettingsToggle(Icons.Default.PrivacyTip, "Tracker Blocking", "Block tracking scripts", isTrackerBlocking) { viewModel.setTrackerBlocking(it) }
                SettingsToggle(Icons.Default.Security, "HTTPS Only", "Upgrade to HTTPS", isHttpsOnly) { viewModel.setHttpsOnly(it) }
                SettingsToggle(Icons.Default.Lock, "Biometric Lock", "Require authentication", isBiometricLock) { viewModel.setBiometricLock(it) }
                SettingsToggle(Icons.Default.Delete, "Clear on Exit", "Clear data when app closes", isClearOnExit) { viewModel.setClearOnExit(it) }
            }

            item {
                SettingsSection("Gestures")
                SettingsToggle(Icons.Default.Gesture, "Swipe Navigation", "Swipe to go back/forward", isSwipeNav) { viewModel.setSwipeNavigation(it) }
            }

            item {
                SettingsSection("Data")
                SettingsButton(Icons.Default.Delete, "Clear History", "Last hour") { viewModel.clearHistoryLastHour() }
                SettingsButton(Icons.Default.Delete, "Clear History", "Last 24 hours") { viewModel.clearHistoryLastDay() }
                SettingsButton(Icons.Default.Delete, "Clear History", "All time") { viewModel.clearHistoryAll() }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun SettingsSection(title: String) {
    Text(
        text = title.uppercase(),
        color = SolaraColors.Accent.copy(alpha = 0.6f),
        fontSize = 11.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(start = 4.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsToggle(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SolaraColors.GlassHighlight)
            .clickable { onCheckedChange(!checked) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = SolaraColors.Accent, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = SolaraColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.W400)
            Text(subtitle, color = SolaraColors.TextGhost, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SolaraColors.Accent,
                uncheckedThumbColor = SolaraColors.TextGhost,
                uncheckedTrackColor = SolaraColors.SurfaceHover
            )
        )
    }
}

@Composable
private fun SettingsButton(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SolaraColors.GlassHighlight)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = SolaraColors.Accent, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = SolaraColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.W400)
            Text(subtitle, color = SolaraColors.TextGhost, fontSize = 12.sp)
        }
    }
}
