package net.lsafer.edgeseek.app.l10n

@Suppress("PropertyName")
data class Strings(
    val branding: Branding = Branding(),
    val label: Label = Label(),
    val stmt: Stmt = Stmt(),
) {
    data class Branding(
        val app_name: String = "Edge Seek",

        val lang_AR: String = "عربي",
        val lang_EN: String = "English",
    )

    data class Label(
        val additional: String = "Additional",
        val appearance: String = "Appearance",
        val application: String = "Application",
        val author: String = "Author",
        val back: String = "Back",
        val cancel: String = "Cancel",
        val clear_log: String = "Clear Log",
        val confirm: String = "Confirm",
        val credits: String = "Credits",
        val dimensions: String = "Dimensions",
        val input: String = "Input",
        val job: String = "Job",
        val links: String = "Links",
        val mandatory: String = "Mandatory",
        val misc: String = "Misc",
        val next: String = "Next",
        val presets: String = "Presets",
        val utility: String = "Utility",
        val version: String = "Version",
        val version_code: String = "Version Code",
        val version_name: String = "Version Name",
        val yes: String = "Yes",
    )

    data class Stmt(
        val exit_application_qm: String = "Exit the application?",
        val welcome_phrase: String = "Welcome",
        val all_setup_phrase: String = "All Set Up!",

        // Pages
        val page_edge_list_headline: String = "Edges",
        val page_edge_list_supporting: String = "Customize the edges",
        val page_edge_list_heading: String = "Choose Edge",
        val page_edge_list_summary: String = "Touch an edge to customize it",

        val page_edge_edit_heading: String = "Edge Configuration",

        val page_permissions_headline: String = "Permissions",
        val page_permissions_supporting: String = "Manage application's permissions",
        val page_permissions_heading: String = "Permissions",

        val page_presets_headline: String = "Presets",
        val page_presets_supporting: String = "Choose a set of configurations",
        val page_presets_heading: String = "Presets",
        val page_presets_summary: String = "Preset configurations",

        val page_about_headline: String = "About",
        val page_about_supporting: String = "Information about this application",
        val page_about_heading: String = "About",

        val page_log_headline: String = "Logs",
        val page_log_supporting: String = "Open log file",
        val page_log_heading: String = "Logs",

        // Permissions
        val display_over_other_apps_headline: String =
            "Display Over Other Apps",
        val display_over_other_apps_supporting: String =
            "Allow this application to draw views floating on your screen",

        val write_system_settings_headline: String =
            "Write System Settings",
        val write_system_settings_supporting: String =
            "Allow this application to edit settings such as brightness level and music volume",

        val ignore_battery_optimizations_headline: String =
            "Ignore Battery Optimizations",
        val ignore_battery_optimizations_supporting: String =
            "Make this application free from the system battery optimizations.",

        // App
        val app_activation_headline: String = "Activation",
        val app_activation_supporting: String = "Toggle to activate or deactivate the application",

        val app_colors_headline: String = "Change Theme",
        val app_colors_value_system: String = "System",
        val app_colors_value_black: String = "Black",
        val app_colors_value_dark: String = "Dark",
        val app_colors_value_light: String = "Light",
        val app_colors_value_white: String = "White",

        val app_auto_boot_headline: String = "Auto Boot",
        val app_auto_boot_supporting: String = "Auto boot the service every time the device booted-up",

        val app_brightness_reset_headline: String = "Brightness Reset",
        val app_brightness_reset_supporting: String = "Turn on auto brightness each time the device turn on to sleep",

        // Edge
        val edge_activation_headline: String = "Activation",
        val edge_activation_supporting: String = "Toggle to activate or deactivate this edge",

        val edge_seek_task_headline: String = "Seek Task",
        val edge_seek_task_value_nothing: String = "Nothing",
        val edge_seek_task_value_control_brightness: String = "Control Brightness",
        val edge_seek_task_value_control_brightness_dimmer: String = "Control Brightness with Dimmer",
        val edge_seek_task_value_control_alarm: String = "Control Alarm",
        val edge_seek_task_value_control_music: String = "Control Music",
        val edge_seek_task_value_control_ring: String = "Control Ring",
        val edge_seek_task_value_control_system: String = "Control System",

        val edge_sensitivity_headline: String = "Sensitivity",
        val edge_sensitivity_supporting: String = "How much you want the edge to be sensitive",

        val edge_thickness_headline: String = "Thickness",
        val edge_thickness_supporting: String = "The thickness of the edge",

        val edge_color_headline: String = "Color",
        val edge_color_supporting: String = "The color of the edge.",

        val edge_seek_toast_headline: String = "Toast",
        val edge_seek_toast_supporting: String = "Display a message with the current volume when seeking",

        val edge_seek_steps_headline: String = "Step on pivot",
        val edge_seek_steps_supporting: String = "Limit seeking range around pivot points",

        val edge_seek_vibrate_headline: String = "Vibrate",
        val edge_seek_vibrate_supporting: String = "The strength of vibration when the edge is touched",

        // About
        val about_website_headline: String = "Website",
        val about_website_supporting: String = "The official EdgeSeek website",

        val about_source_code_headline: String = "Source Code",
        val about_source_code_supporting: String = "The application source code",

        val about_reintroduce_headline: String = "Re-Introduce",
        val about_reintroduce_supporting: String = "Run the introduction wizard",

        // Presets
        val preset_standard_headline: String = "Standard",
        val preset_standard_supporting: String = "Control music audio left and brightness right",

        val preset_standard_c_headline: String = "Standard (Centered)",
        val preset_standard_c_supporting: String = "Same as Standard but with centered bars",

        val preset_brightness_headline: String = "Brightness Only",
        val preset_brightness_supporting: String = "Only control brightness from the right",

        val preset_brightness_c_headline: String = "Brightness Only (Centered)",
        val preset_brightness_c_supporting: String = "Same as Brightness Only but with centered bars",

        val preset_brightness_d_headline: String = "Double Brightness",
        val preset_brightness_d_supporting: String = "Control brightness from both sides",

        val preset_brightness_dc_headline: String = "Double Brightness (Centered)",
        val preset_brightness_dc_supporting: String = "Same as Double Brightness but with centered bars (LSafer's choice)",

        // Preset Util

        val show_all_headline: String = "Show All",
        val show_all_supporting: String = "Increase the opacity of all edges",

        val hide_all_headline: String = "Hide All",
        val hide_all_supporting: String = "Decrease the opacity of all edges",

        val stealth_all_headline: String = "Stealth All",
        val stealth_all_supporting: String = "Decrease the opacity of all edges (Compatibility Mode)",

        // Foreground Service
        val foreground_noti_title: String = "Running In Background",
        val foreground_noti_text: String = """
                Allows the application to work in background.
                Recommended disabling this notification since it has no real value.
        """.trimIndent(),
    )
}
