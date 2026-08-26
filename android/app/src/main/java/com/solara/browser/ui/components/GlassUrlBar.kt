package com.solara.browser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlassUrlBar(
    url: String,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onToggleTabs: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf(url) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(36.dp),
                clip = false,
                ambientColor = Color(0x1EFFB84D),
                spotColor = Color(0x1EFFB84D)
            )
            .clip(RoundedCornerShape(36.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.05f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.25f),
                shape = RoundedCornerShape(36.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Security indicator (colored dot)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (url.startsWith("https://")) Color(0xFF4ADE80) else Color(0xFFF87171),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )

            // URL input area (clickable to edit)
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent),
                color = Color.Transparent,
                onClick = { isFocused = true }
            ) {
                if (isFocused) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        ),
                        placeholder = {
                            Text(
                                "Enter URL",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White.copy(alpha = 0.3f),
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.White.copy(alpha = 0.1f),
                            unfocusedContainerColor = Color.Transparent,
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                if (inputText.isNotBlank()) {
                                    onNavigate(inputText)
                                    isFocused = false
                                }
                            }
                        )
                    )
                } else {
                    Text(
                        text = url,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            // Refresh button / Loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White.copy(alpha = 0.8f),
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Toggle tabs button (real icon)
            IconButton(
                onClick = onToggleTabs,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Toggle tabs",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
