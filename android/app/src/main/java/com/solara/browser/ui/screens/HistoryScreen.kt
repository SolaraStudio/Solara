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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.data.model.HistoryEntity
import com.solara.browser.ui.theme.SolaraColors
import com.solara.browser.ui.viewmodel.BrowserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: BrowserViewModel,
    onBack: () -> Unit,
    onEntryClick: (String) -> Unit
) {
    val history by viewModel.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SolaraColors.SolaraColors.BackgroundGradient)
    ) {
        TopAppBar(
            title = {
                Text("History", color = SolaraColors.TextPrimary, fontWeight = FontWeight.W600)
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SolaraColors.TextSecondary)
                }
            },
            actions = {
                IconButton(onClick = { viewModel.clearHistory() }) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear", tint = SolaraColors.TextTertiary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SolaraColors.Midnight),
            modifier = Modifier.statusBarsPadding()
        )

        if (history.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Icon(
                    Icons.Default.History, contentDescription = null,
                    tint = SolaraColors.TextGhost, modifier = Modifier.size(56.dp)
                )
                Text("No history yet", color = SolaraColors.TextTertiary, fontSize = 15.sp, modifier = Modifier.padding(top = 16.dp))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
            ) {
                items(history) { entry ->
                    HistoryItem(
                        entry = entry,
                        onClick = { onEntryClick(entry.url) },
                        onDelete = { viewModel.deleteHistoryEntry(entry.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(
    entry: HistoryEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
    val dateStr = dateFormat.format(Date(entry.timestamp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SolaraColors.GlassHighlight)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.History, contentDescription = null,
            tint = SolaraColors.TextGhost, modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.title, color = SolaraColors.TextPrimary,
                fontSize = 14.sp, fontWeight = FontWeight.W400,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Row {
                Text(
                    text = entry.url, color = SolaraColors.TextGhost,
                    fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(text = dateStr, color = SolaraColors.TextGhost, fontSize = 10.sp)
            }
        }
        IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = SolaraColors.TextGhost, modifier = Modifier.size(16.dp))
        }
    }
}
