package com.solara.browser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.ui.theme.SolaraColors
import com.solara.browser.ui.viewmodel.BrowserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    viewModel: BrowserViewModel,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SolaraColors.SolaraColors.BackgroundGradient)
    ) {
        TopAppBar(
            title = {
                Text("Downloads", color = SolaraColors.TextPrimary, fontWeight = FontWeight.W600)
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SolaraColors.TextSecondary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SolaraColors.Midnight),
            modifier = Modifier.statusBarsPadding()
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Icon(
                Icons.Default.FileDownload, contentDescription = null,
                tint = SolaraColors.TextGhost, modifier = Modifier.size(56.dp)
            )
            Text("No downloads yet", color = SolaraColors.TextTertiary, fontSize = 15.sp, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
