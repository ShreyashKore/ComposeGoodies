/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.composegoodies.library.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    iconSize: Dp = 34.dp,
    fontSize: TextUnit = 12.sp,
    spacing: Dp = 0.dp,
    onClick: () -> Unit
) {
    IconTextButton(
        modifier = modifier,
        painter = rememberVectorPainter(imageVector),
        text = text,
        color = color,
        iconSize = iconSize,
        fontSize = fontSize,
        spacing = spacing,
        onClick = onClick
    )
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    iconSize: Dp = 34.dp,
    fontSize: TextUnit = 12.sp,
    spacing: Dp = 0.dp,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 32.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = color
        )
        Spacer(
            modifier = Modifier.padding(spacing)
        )
        Text(
            text = text,
            fontSize = fontSize,
            color = color
        )
    }
}