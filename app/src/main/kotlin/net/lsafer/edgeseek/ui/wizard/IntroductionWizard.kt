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
package net.lsafer.edgeseek.ui.wizard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.LocalNavController
import net.lsafer.edgeseek.model.rememberApplicationData
import net.lsafer.edgeseek.ui.screen.MainScreenRoute
import net.lsafer.edgeseek.ui.screen.PermissionsScreenContent
import net.lsafer.edgeseek.ui.screen.PresetsScreenContent
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding

const val IntroductionWizardRoute = "wizard:introduction"

@Composable
fun IntroductionWizard() {
    val navController = LocalNavController.current
    var data by rememberApplicationData()
    var step by remember { mutableStateOf(0) }

    BackHandler(step > 0) {
        step--
    }

    when (step) {
        -1 -> LaunchedEffect(Unit) {
            navController.navigateUp()
        }
        0 -> IntroductionScreenWrapper(
            onConfirm = { step++ },
            onCancel = { step-- },
            content = { PermissionsScreenContent() }
        )
        1 -> IntroductionScreenWrapper(
            onConfirm = { step++ },
            onCancel = { step-- },
            content = { PresetsScreenContent() }
        )
        2 -> LaunchedEffect(Unit) {
            data = data.copy(introduced = true)
            while (navController.popBackStack());
            navController.navigate(MainScreenRoute)
        }
    }
}

@Composable
fun IntroductionScreenWrapper(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(Modifier.padding(
        top = StatusBarPadding,
        bottom = NavigationBarPadding
    )) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().weight(1f)) {
                content()
            }

            Surface(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(vertical = 5.dp, horizontal = 75.dp)) {
                    TextButton(onClick = { onCancel() }) {
                        Text("Back")
                    }
                    Spacer(Modifier.fillMaxWidth().weight(1f))
                    TextButton(onClick = { onConfirm() }) {
                        Text("Next", color = MaterialTheme.colors.secondary)
                    }
                }
            }
        }
    }
}
