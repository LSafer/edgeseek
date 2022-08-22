/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */

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
