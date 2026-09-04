package com.solara.browser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solara.browser.ui.theme.SolaraColors

@Composable
fun BottomToolbar(
    canGoBack: Boolean,
    canGoForward: Boolean,
    isBookmarked: Boolean,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onHome: () -> Unit,
    onBookmark: () -> Unit,
    onShare: () -> Unit,
    onSearch: () -> Unit,
    onMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(brush = SolaraColors.SolaraColors.ToolbarGradient)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            enabled = canGoBack,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = if (canGoBack) SolaraColors.TextPrimary else SolaraColors.TextGhost,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onForward,
            enabled = canGoForward,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Forward",
                tint = if (canGoForward) SolaraColors.TextPrimary else SolaraColors.TextGhost,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onHome,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = "Home",
                tint = SolaraColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onBookmark,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = if (isBookmarked) SolaraColors.Accent else SolaraColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onShare,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.Share,
                contentDescription = "Share",
                tint = SolaraColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onSearch,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Find",
                tint = SolaraColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onMore,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More",
                tint = SolaraColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
