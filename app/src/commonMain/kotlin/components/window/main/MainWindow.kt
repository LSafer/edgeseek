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
package net.lsafer.edgeseek.app.components.window.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.page.about.AboutPage
import net.lsafer.edgeseek.app.components.page.edge_edit.EdgeEditPage
import net.lsafer.edgeseek.app.components.page.edge_list.EdgeListPage
import net.lsafer.edgeseek.app.components.page.home.HomePage
import net.lsafer.edgeseek.app.components.page.log.LogPage
import net.lsafer.edgeseek.app.components.page.permissions.PermissionsPage
import net.lsafer.edgeseek.app.components.page.presets.PresetsPage
import net.lsafer.edgeseek.app.components.wizard.introduction.IntroductionWizard
import net.lsafer.sundry.compose.simplenav.current

@Composable
fun MainWindow(local: Local, modifier: Modifier = Modifier) {
    when (val route = local.navController.current) {
        is UniRoute.HomePage ->
            HomePage(local, route, modifier)

        is UniRoute.EdgeListPage ->
            EdgeListPage(local, route, modifier)

        is UniRoute.EdgeEditPage ->
            EdgeEditPage(local, route, modifier)

        is UniRoute.PermissionsPage ->
            PermissionsPage(local, route, modifier)

        is UniRoute.PresetsPage ->
            PresetsPage(local, route, modifier)

        is UniRoute.AboutPage ->
            AboutPage(local, route, modifier)

        is UniRoute.LogPage ->
            LogPage(local, route, modifier)

        is UniRoute.IntroductionWizard ->
            IntroductionWizard(local, route, modifier)

        else -> {}
    }
}
