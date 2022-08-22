package net.lsafer.edgeseek

import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.serialization.Serializable
import net.lsafer.datastore.jsonPreference
import net.lsafer.datastore.observeJsonPreference
import net.lsafer.edgeseek.model.EdgeData
import java.util.*

val Context.appDataStore by preferencesDataStore("appData")
