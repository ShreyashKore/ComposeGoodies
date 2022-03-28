/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.composegoodies.library.utils

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
inline fun <reified T : Activity> rememberActivity(): T {
    val context = LocalContext.current
    val activity = remember {
        context.let {
            var ctx = it
            while (ctx is ContextWrapper) {
                if (ctx is T) {
                    return@let ctx
                }
                ctx = ctx.baseContext
            }
            throw IllegalStateException(
                "Expected an activity context for getting Activity" +
                        "but instead found: $ctx"
            )
        }
    }
    return activity
}