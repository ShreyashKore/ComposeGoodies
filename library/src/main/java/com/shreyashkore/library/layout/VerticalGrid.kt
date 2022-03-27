/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.library.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max


/**
 * Vertical Grid layout (Note: should not be used for large no of items. Not as performant as LazyGrid)
 * @param columns no of columns in grid
 * @param autoSizeChildren should the grid layout size items automatically
 * @param minWidth minimum width of item (might get reduced automatically if innerPadding is used)
 * @param maxWidth maximum width of item (might get reduced automatically if innerPadding is used)
 * @param innerPadding padding in between items
 * @param centerItems center the child items so that the partially filled rows will align their children to the center
 *
 * */
@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    autoSizeChildren: Boolean = true,
    minWidth: Dp = Dp.Unspecified,
    maxWidth: Dp = Dp.Unspecified,
    innerPadding: Dp = 0.dp,
    centerItems: Boolean = true,
    content: @Composable () -> Unit
) {
    val minWidthPx =
        if (minWidth != Dp.Unspecified) with(LocalDensity.current) { minWidth.roundToPx() }
        else 0

    val maxWidthPx =
        if (maxWidth != Dp.Unspecified) with(LocalDensity.current) { maxWidth.roundToPx() }
        else Constraints.Infinity

    val autoSizeConstraints = Constraints(minWidthPx, maxWidthPx)

    val innerPaddingPx = with(LocalDensity.current) { innerPadding.roundToPx() }

    VerticalGrid(
        modifier = modifier,
        columns = columns,
        innerPadding = innerPaddingPx,
        autoSizeChildren = autoSizeChildren,
        autoSizeConstraints = autoSizeConstraints,
        centerItems = centerItems,
        content = content
    )
}


@Composable
private fun VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    innerPadding: Int = 0,
    autoSizeChildren: Boolean = true,
    autoSizeConstraints: Constraints = Constraints(),
    centerItems: Boolean = true,
    content: @Composable () -> Unit
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        var cols = columns
        if (autoSizeChildren) {
            cols =
                max(
                    (constraints.maxWidth.toFloat() / autoSizeConstraints.minWidth.toFloat()).toInt(),
                    1
                )
            if (cols > 3) {
                cols =
                    (constraints.maxWidth.toFloat() / autoSizeConstraints.maxWidth.toFloat()).toInt() + 1
            }
        }

        val innerCols = cols - 1

        val itemWidth = (constraints.maxWidth - innerCols * innerPadding) / cols

        // Keep given height constraints, but set an exact width
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )

        // Measure each item with these constraints
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        // Track each columns height so we can calculate the overall height
        val columnHeights = Array(cols) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % cols
            columnHeights[column] += placeable.height
        }

        val height = (columnHeights.maxOrNull() ?: constraints.minHeight)
            .coerceAtMost(constraints.maxHeight)


        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            if (centerItems) {
                placeGridCentered(placeables, cols, itemWidth, innerPadding, constraints.maxWidth)
            } else {
                placeGrid(placeables, cols, itemWidth, innerPadding)
            }
        }

    }
}


private fun Placeable.PlacementScope.placeGridCentered(
    placeables: List<Placeable>,
    cols: Int,
    itemWidth: Int,
    innerPadding: Int,
    maxWidth: Int
) {

    val columnY = Array(cols) { 0 }
    val center = maxWidth / 2

    placeables
        .windowed(size = cols, step = cols, partialWindows = true)
        .forEachIndexed { rowNo, placeableGroup ->
            var xDim = 0
            val groupWidth =
                placeableGroup.size * itemWidth + (placeableGroup.size - 1) * innerPadding

            placeableGroup
                .forEachIndexed { columnNo, placeable ->

                    placeable
                        .placeRelative(
                            x = (center - groupWidth / 2) + xDim,
                            y = columnY[columnNo]
                        )

                    xDim += itemWidth + innerPadding
                    columnY[columnNo] += placeable.height + innerPadding

                }

        }
}


private fun Placeable.PlacementScope.placeGrid(
    placeables: List<Placeable>,
    cols: Int,
    itemWidth: Int,
    innerPadding: Int,
) {
    val columnY = Array(cols) { 0 }

    placeables
        .windowed(size = cols, step = cols, partialWindows = true)
        .forEachIndexed { rowNo, placeableGroup ->
            var xDim = 0

            placeableGroup
                .forEachIndexed { columnNo, placeable ->

                    placeable
                        .placeRelative(
                            x = xDim,
                            y = columnY[columnNo]
                        )
                    xDim += itemWidth + innerPadding
                    columnY[columnNo] += placeable.height + innerPadding

                }

        }
}


@Composable
private fun Ref_VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    autoSizeChildren: Boolean = true,
    autoSizeConstraints: Constraints = Constraints(),
    content: @Composable () -> Unit
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        val itemWidth = constraints.maxWidth / columns

        // Keep given height constraints, but set an exact width
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )
        // Measure each item with these constraints
        val placeables = measurables.map { it.measure(itemConstraints) }
        // Track each columns height so we can calculate the overall height
        val columnHeights = Array(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight)
            .coerceAtMost(constraints.maxHeight)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // Track the Y co-ord per column we have placed up to
            val columnY = Array(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                placeable.placeRelative(
                    x = column * itemWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}