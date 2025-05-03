package net.lsafer.edgeseek.app.components.page.permissions

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute

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

@Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")
@Composable
expect fun PermissionsPageContent(
    local: Local,
    modifier: Modifier = Modifier,
)
