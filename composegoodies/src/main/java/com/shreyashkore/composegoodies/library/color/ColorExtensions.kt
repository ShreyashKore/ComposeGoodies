/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.composegoodies.library.color

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Composable
fun Color.overBackground() = this.compositeOver(MaterialTheme.colorScheme.background)

@Composable
fun Color.overSurface() = this.compositeOver(MaterialTheme.colorScheme.surface)