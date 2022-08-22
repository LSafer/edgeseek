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
package net.lsafer.edgeseek

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.lsafer.edgeseek.model.rememberApplicationData
import net.lsafer.edgeseek.ui.screen.*
import net.lsafer.edgeseek.ui.wizard.IntroductionWizard
import net.lsafer.edgeseek.ui.wizard.IntroductionWizardRoute
import java.util.*

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    val data by rememberApplicationData()

    LaunchedEffect(Unit) {
        while (navController.popBackStack());
        navController.navigate(when {
            data.introduced -> MainScreenRoute
            else -> IntroductionWizardRoute
        })
    }

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController, startDestination = "splash") {
            composable("splash") { }
            composable(MainScreenRoute) { MainScreen() }
            composable(EdgesScreenRoute) { EdgesScreen() }
            composable(PermissionsScreenRoute) { PermissionsScreen() }
            composable(PresetsScreenRoute) { PresetsScreen() }
            composable(AboutScreenRoute) { AboutScreen() }
            composable(IntroductionWizardRoute) { IntroductionWizard() }
            composable(EdgeScreenRoute) {
                EdgeScreen(id = it.arguments?.getString("id", null) ?: UUID.randomUUID().toString())
            }
        }
    }
}
