package net.lsafer.edgeseek.app.l10n.strings

import net.lsafer.edgeseek.app.l10n.Strings

val Strings_en = Strings(
    branding = Strings.Branding(
        app_name = "Edge Seek",

        light_theme = "Light",
        dark_theme = "Dark",
        white_theme = "White",
        black_theme = "Black",

        lang_AR = "Arabic",
        lang_EN = "English",
    ),
    label = Strings.Label(
        cancel = "Cancel",
        confirm = "Confirm",

        yes = "Yes",
    ),
    stmt = Strings.Stmt(
        display_over_other_apps_headline =
            "Display Over Other Apps",
        display_over_other_apps_supporting =
            "Allow this application to draw views floating on your screen",

        write_system_settings_headline =
            "Write System Settings",
        write_system_settings_supporting =
            "Allow this application to edit settings such as brightness level and music volume",

        ignore_battery_optimizations_headline =
            "Ignore Battery Optimizations",
        ignore_battery_optimizations_supporting =
            "Make this application free from the system battery optimizations.",

        exit_application_qm = "Exit the application?",
    ),
)
