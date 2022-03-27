package com.shreyashkore.composegoodies.color

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Composable
fun Color.overBackground() = this.compositeOver(MaterialTheme.colors.background)

@Composable
fun Color.overSurface() = this.compositeOver(MaterialTheme.colors.surface)