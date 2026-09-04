package com.solara.browser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.ui.theme.SolaraColors

data class Shortcut(
    val title: String,
    val url: String,
    val icon: ImageVector = Icons.Default.Language,
    val color: Color = SolaraColors.Accent
)

@Composable
fun HomeScreen(
    shortcuts: List<Shortcut>,
    onShortcutClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = SolaraColors.SolaraColors.BackgroundGradient)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = SolaraColors.SolaraColors.GlowGradient)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "solara",
                color = SolaraColors.TextPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.W300,
                letterSpacing = (-1.5).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "a calmer internet",
                color = SolaraColors.TextGhost,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(56.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(shortcuts) { shortcut ->
                    ShortcutItem(
                        shortcut = shortcut,
                        onClick = { onShortcutClick(shortcut.url) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShortcutItem(
    shortcut: Shortcut,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(shortcut.color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                shortcut.icon,
                contentDescription = shortcut.title,
                tint = shortcut.color,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = shortcut.title,
            color = SolaraColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.W400,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
