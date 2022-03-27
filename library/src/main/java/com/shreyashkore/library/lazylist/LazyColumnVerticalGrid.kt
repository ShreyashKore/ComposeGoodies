/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.library.lazylist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun <T> LazyListScope.gridItems(
    items: List<T>,
    columnCount: Int = 2,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    content: @Composable BoxScope.(Int, T) -> Unit,
) {
    val size = items.size
    val rows = if (size == 0) size else (size - 1) / columnCount + 1
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            (0..columnCount).forEach { columnIndex ->
                val i = rowIndex * columnCount + columnIndex
                if (i < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        content(i, items[i])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}