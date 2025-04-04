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
package net.lsafer.edgeseek.app.components.wizard.introduction

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.PK_WIZ_INTRO
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.page.permissions.PermissionsPageContent
import net.lsafer.edgeseek.app.components.page.presets.PresetsPageContent
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.sundry.storage.edit
import org.cufy.json.set

@Composable
fun IntroductionWizard(
    local: Local,
    route: UniRoute.IntroductionWizard,
    modifier: Modifier = Modifier,
) {
    val steps = UniRoute.IntroductionWizard.Step.entries

    val onStepCancel: () -> Unit = {
        local.navController.back()
    }

    val onStepConfirm: () -> Unit = {
        val i = route.step.ordinal + 1

        if (i <= steps.size)
            local.navController.push(route.copy(step = steps[i]))
    }

    val onComplete: () -> Unit = {
        local.dataStore.edit { it[PK_WIZ_INTRO] = true }
        while (local.navController.back());
        local.navController.push(UniRoute.HomePage)
    }

    when (route.step) {
        UniRoute.IntroductionWizard.Step.Welcome -> {
            IntroductionWizardWrapper(
                local = local,
                onConfirm = onStepConfirm,
                onCancel = onStepCancel,
                modifier = modifier,
            ) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(strings.stmt.welcome_phrase)
                }
            }
        }

        UniRoute.IntroductionWizard.Step.Permissions ->
            IntroductionWizardWrapper(
                local = local,
                onConfirm = onStepConfirm,
                onCancel = onStepCancel,
                modifier = modifier,
                content = { PermissionsPageContent(local) }
            )

        UniRoute.IntroductionWizard.Step.Presets ->
            IntroductionWizardWrapper(
                local = local,
                onConfirm = onStepConfirm,
                onCancel = onStepCancel,
                modifier = modifier,
                content = { PresetsPageContent(local) }
            )

        UniRoute.IntroductionWizard.Step.Done -> {
            IntroductionWizardWrapper(
                local = local,
                onConfirm = onComplete,
                onCancel = onStepCancel,
                modifier = modifier,
            ) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(strings.stmt.all_setup_phrase)
                }
            }
        }
    }
}

@Composable
fun IntroductionWizardWrapper(
    local: Local,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Scaffold(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        snackbarHost = {
            SnackbarHost(local.snackbar)
        },
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                content()
            }

            Surface(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(vertical = 5.dp, horizontal = 75.dp)) {
                    TextButton(onClick = { onCancel() }) {
                        Text(strings.label.back)
                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    TextButton({ onConfirm() }) {
                        Text(strings.label.next, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}
