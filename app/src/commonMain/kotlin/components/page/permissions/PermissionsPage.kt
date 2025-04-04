package net.lsafer.edgeseek.app.components.page.permissions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.lib.ListDivider
import net.lsafer.edgeseek.app.components.lib.ListHeader
import net.lsafer.edgeseek.app.components.lib.ListSectionTitle
import net.lsafer.edgeseek.app.l10n.strings

@Composable
fun PermissionsPage(
    local: Local,
    route: UniRoute.PermissionsPage,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        snackbarHost = {
            SnackbarHost(local.snackbar)
        },
    ) { innerPadding ->
        PermissionsPageContent(local, Modifier.padding(innerPadding))
    }
}

@Composable
fun PermissionsPageContent(
    local: Local,
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        ListHeader(title = strings.stmt.page_permissions_heading)
        ListSectionTitle(title = strings.label.mandatory)
        PermissionsPage_ListItem_display_over_other_apps()
        PermissionsPage_ListItem_write_system_settings()

        ListDivider()
        ListSectionTitle(title = strings.label.additional)
        PermissionsPage_ListItem_ignore_battery_optimizations()

        Spacer(Modifier.height(50.dp))
    }
}
