package net.lsafer.edgeseek.app

import net.lsafer.edgeseek.app.data.settings.EdgePos

/* ============= ------------------ ============= */

sealed interface UniEvent {
    data object StartService : UniEvent
    data object RefreshRequest : UniEvent
    data object FocusRequest : UniEvent
    data object ClearLog : UniEvent

    data class OpenUrlRequest(
        val url: String,
        val force: Boolean = false,
    ) : UniEvent
}

/* ============= ------------------ ============= */

sealed interface UniRoute {
    data object HomePage : UniRoute
    data object EdgeListPage : UniRoute
    data class EdgeEditPage(val pos: EdgePos) : UniRoute
    data object PermissionsPage : UniRoute
    data object PresetsPage : UniRoute
    data object AboutPage : UniRoute
    data object LogPage : UniRoute

    data class IntroductionWizard(val step: Step = Step.Welcome) : UniRoute {
        enum class Step {
            Welcome,
            Permissions,
            Presets,
            Done,
        }
    }
}

/* ============= ------------------ ============= */
