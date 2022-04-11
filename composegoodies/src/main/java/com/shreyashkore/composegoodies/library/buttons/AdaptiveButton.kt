/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.composegoodies.library.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shreyashkore.composegoodies.library.text.AdaptiveText

@Composable
fun AdaptiveButton(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    text: String,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    spacing: Dp = 5.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    iconSize: Dp = 24.dp,
    onClick: () -> Unit
) {
    AdaptiveButton(
        modifier = modifier,
        text = text,
        leadingIcon = leadingIcon?.let { rememberVectorPainter(image = it) },
        trailingIcon = trailingIcon?.let { rememberVectorPainter(image = it) },
        enabled = enabled,
        shape = shape,
        spacing = spacing,
        textStyle = textStyle,
        contentPadding = contentPadding,
        colors = colors,
        border = border,
        elevation = elevation,
        iconSize = iconSize,
        onClick = onClick
    )

}


@Composable
fun AdaptiveButton(
    modifier: Modifier = Modifier,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    text: String,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    spacing: Dp = 5.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    iconSize: Dp = 24.dp,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = shape,
        elevation = elevation,
        colors = colors,
        border = border,
        contentPadding = contentPadding
    ) {

        if (leadingIcon != null) {
            Row {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = leadingIcon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(spacing))
            }
        }

        AdaptiveText(
            text = text,
            style = textStyle,
            fontSize = MaterialTheme.typography.labelLarge.fontSize
        )

        if (trailingIcon != null) {
            Row {
                Spacer(modifier = Modifier.padding(spacing))
                Icon(
                    painter = trailingIcon,
                    contentDescription = null
                )
            }
        }

    }
}