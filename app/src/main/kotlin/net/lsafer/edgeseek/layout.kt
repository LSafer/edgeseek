package net.lsafer.edgeseek

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.lsafer.edgeseek.ui.screen.*
import java.util.*

@Composable
fun MainLayout() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController, startDestination = MainScreenRoute) {
            composable(MainScreenRoute) { MainScreen() }
            composable(EdgesScreenRoute) { EdgesScreen() }
            composable(PermissionsScreenRoute) { PermissionsScreen() }
            composable(PresetsScreenRoute) { PresetsScreen() }
            composable(AboutScreenRoute) { AboutScreen() }
            composable(EdgeScreenRoute) {
                EdgeScreen(id = it.arguments?.getString("id", null) ?: UUID.randomUUID().toString())
            }
        }
    }
}
