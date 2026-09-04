package com.solara.browser.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.ui.theme.SolaraColors

data class Tab(
    val id: String,
    val title: String,
    val url: String,
    var isActive: Boolean = false
)

data class Workspace(
    val id: String,
    val name: String,
    val icon: ImageVector = Icons.Default.Language,
    val color: Long = 0xFF7C5CFC,
    val tabCount: Int = 0
)

@Composable
fun VerticalTabs(
    tabs: List<Tab>,
    workspaces: List<Workspace>,
    currentTabId: String?,
    activeWorkspaceId: String,
    isVisible: Boolean,
    onTabSelected: (Tab) -> Unit,
    onTabClosed: (String) -> Unit,
    onNewTab: () -> Unit,
    onClose: () -> Unit,
    onWorkspaceSelected: (String) -> Unit,
    onNewWorkspace: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = modifier
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(0.dp, 24.dp, 24.dp, 0.dp),
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.5f),
                    spotColor = Color.Black.copy(alpha = 0.3f)
                )
                .clip(RoundedCornerShape(0.dp, 24.dp, 24.dp, 0.dp))
                .background(brush = SolaraColors.SolaraColors.SidebarGradient)
                .border(
                    width = 1.dp,
                    color = SolaraColors.GlassBorder,
                    shape = RoundedCornerShape(0.dp, 24.dp, 24.dp, 0.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TABS",
                        color = SolaraColors.TextGhost,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W700,
                        letterSpacing = 2.sp
                    )
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SolaraColors.TextGhost,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(workspaces) { workspace ->
                        WorkspaceChip(
                            workspace = workspace,
                            isActive = workspace.id == activeWorkspaceId,
                            onClick = { onWorkspaceSelected(workspace.id) }
                        )
                    }
                    item {
                        TextButton(
                            onClick = onNewWorkspace,
                            modifier = Modifier.height(30.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "New workspace",
                                tint = SolaraColors.TextGhost,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(tabs) { tab ->
                        TabItem(
                            tab = tab,
                            isActive = tab.id == currentTabId,
                            onClick = { onTabSelected(tab) },
                            onClose = { onTabClosed(tab.id) }
                        )
                    }
                }

                Button(
                    onClick = onNewTab,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SolaraColors.Accent.copy(alpha = 0.12f),
                        contentColor = SolaraColors.AccentBright
                    ),
                    border = BorderStroke(1.dp, SolaraColors.Accent.copy(alpha = 0.2f))
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "New tab",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Tab", fontSize = 13.sp, fontWeight = FontWeight.W500)
                }
            }
        }
    }
}

@Composable
private fun WorkspaceChip(
    workspace: Workspace,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isActive) {
        Color(workspace.color).copy(alpha = 0.15f)
    } else {
        SolaraColors.GlassHighlight
    }
    val borderColor = if (isActive) {
        Color(workspace.color).copy(alpha = 0.3f)
    } else {
        SolaraColors.GlassBorder
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            workspace.icon,
            contentDescription = null,
            tint = if (isActive) SolaraColors.AccentBright else SolaraColors.TextGhost,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = workspace.name,
            color = if (isActive) SolaraColors.TextPrimary else SolaraColors.TextTertiary,
            fontSize = 11.sp,
            fontWeight = if (isActive) FontWeight.W600 else FontWeight.W400
        )
        if (workspace.tabCount > 0) {
            Text(
                text = "${workspace.tabCount}",
                color = SolaraColors.TextGhost,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun TabItem(
    tab: Tab,
    isActive: Boolean,
    onClick: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = SolaraColors.SolaraColors.itemBackground(isActive))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tab.title,
            color = if (isActive) SolaraColors.TextPrimary else SolaraColors.TextTertiary,
            fontSize = 13.sp,
            fontWeight = if (isActive) FontWeight.W500 else FontWeight.W400,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close tab",
                tint = SolaraColors.TextGhost,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
