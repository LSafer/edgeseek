package net.lsafer.edgeseek.app.components.page.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.components.lib.ListDivider
import net.lsafer.edgeseek.app.components.lib.ListHeader
import net.lsafer.edgeseek.app.components.lib.ListSectionTitle
import net.lsafer.edgeseek.app.l10n.strings

@Composable
actual fun PermissionsPageContent(
    local: Local,
    modifier: Modifier,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        ListHeader(title = strings.stmt.page_permissions_heading)
        ListSectionTitle(title = strings.label.mandatory)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            PermissionsPage_ListItem_allow_restricted_permissions()
        }

        PermissionsPage_ListItem_display_over_other_apps()
        PermissionsPage_ListItem_write_system_settings()

        ListDivider()
        ListSectionTitle(title = strings.label.additional)
        PermissionsPage_ListItem_ignore_battery_optimizations()
        PermissionsPage_ListItem_accessibility_service()

        Spacer(Modifier.height(50.dp))
    }
}
