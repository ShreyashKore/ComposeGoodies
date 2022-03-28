/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.composegoodies.library.interaction

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.DpOffset

fun Modifier.clickableWithPosition(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: (offset: DpOffset) -> Unit
): Modifier = composed {

    val interactionSource = remember { MutableInteractionSource() }
    val onClickState = rememberUpdatedState(onClick)

    val pressedInteraction = remember { mutableStateOf<PressInteraction.Press?>(null) }
    val isRootInScrollableContainer = isComposeRootInScrollableContainer()


    pointerInput(interactionSource, enabled) {
        detectTapGestures(
            onPress = { offset ->
//                if (enabled) {
//                    handlePressInteraction(
//                        offset,
//                        interactionSource,
//                        pressedInteraction,
//                        delayPressInteraction
//                    )
//                }
            },
            onTap = { offset ->
                if (enabled) onClickState.value.invoke(
                    DpOffset(offset.x.toDp(), offset.y.toDp())
                )
            }
        )

    }
        .indication(interactionSource, rememberRipple())
        .semantics {
            if (role != null) this.role = role
            if (onClickLabel != null) this.onClick(label = onClickLabel, action = null)
        }
}

@Composable
fun isComposeRootInScrollableContainer(): () -> Boolean {
    val view = LocalView.current
    return {
        view.isInScrollableViewGroup()
    }
}

private fun View.isInScrollableViewGroup(): Boolean {
    var p = parent
    while (p != null && p is ViewGroup) {
        if (p.shouldDelayChildPressedState()) {
            return true
        }
        p = p.parent
    }
    return false
}