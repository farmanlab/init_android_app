package com.farmanlab.init_app.ui.settings

interface SettingsContract {
    data class Setting(
        val id: String,
        val title: String,
        val description: String?
    )
}
