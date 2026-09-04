package com.solara.browser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solara.browser.ui.theme.SolaraColors

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
            .height(56.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                clip = false,
                ambientColor = SolaraColors.Accent.copy(alpha = 0.06f),
                spotColor = SolaraColors.Accent.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(brush = SolaraColors.SolaraColors.UrlBarGradient)
            .border(
                width = 1.dp,
                color = SolaraColors.GlassBorder,
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(7.dp)
                    .background(
                        color = if (url.startsWith("https://")) SolaraColors.Success else SolaraColors.Error,
                        shape = CircleShape
                    )
            )

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp)),
                color = Color.Transparent,
                onClick = { isFocused = true }
            ) {
                if (isFocused) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            color = SolaraColors.TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400
                        ),
                        placeholder = {
                            Text(
                                "Search or enter URL",
                                color = SolaraColors.TextGhost,
                                fontSize = 15.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SolaraColors.Accent.copy(alpha = 0.3f),
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = SolaraColors.Accent,
                            focusedTextColor = SolaraColors.TextPrimary,
                            unfocusedTextColor = SolaraColors.TextPrimary,
                            focusedContainerColor = SolaraColors.Surface.copy(alpha = 0.5f),
                            unfocusedContainerColor = Color.Transparent,
                            focusedPlaceholderColor = SolaraColors.TextGhost,
                            unfocusedPlaceholderColor = SolaraColors.TextGhost
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
                        color = SolaraColors.TextSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W400,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                    )
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(18.dp),
                    color = SolaraColors.Accent,
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = SolaraColors.TextTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            IconButton(
                onClick = onToggleTabs,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Tabs",
                    tint = SolaraColors.TextTertiary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
