package net.lsafer.edgeseek.app.l10n

@Suppress("PropertyName")
data class Strings(
    val branding: Branding,
    val label: Label,
    val stmt: Stmt,
) {
    data class Branding(
        val app_name: String,

        val light_theme: String,
        val dark_theme: String,
        val white_theme: String,
        val black_theme: String,

        val lang_AR: String,
        val lang_EN: String,
    )

    data class Label(
        val cancel: String,
        val confirm: String,

        val yes: String,
    )

    data class Stmt(
        val display_over_other_apps_headline: String,
        val display_over_other_apps_supporting: String,

        val write_system_settings_headline: String,
        val write_system_settings_supporting: String,

        val ignore_battery_optimizations_headline: String,
        val ignore_battery_optimizations_supporting: String,

        val exit_application_qm: String,
    )
}
