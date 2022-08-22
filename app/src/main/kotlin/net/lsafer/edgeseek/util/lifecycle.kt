@file:SuppressLint("ComposableNaming")

package net.lsafer.edgeseek.util

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun Lifecycle.observeAsState(): Lifecycle.State {
    var state by remember { mutableStateOf(this.currentState) }

    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state = event.targetState
        }

        this@observeAsState.addObserver(observer)

        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }

    return state
}
