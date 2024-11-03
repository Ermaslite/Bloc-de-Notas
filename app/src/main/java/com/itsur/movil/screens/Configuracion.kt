package com.itsur.movil.screens

import android.app.Activity
import android.content.Context
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsur.movil.R
import java.util.Locale

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val currentLanguage = sharedPreferences.getString("language", "en") ?: "en"
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text(text = stringResource(R.string.language), style = MaterialTheme.typography.bodySmall)
        TextButton(onClick = { showLanguageDialog = true }) {
            Text(text = if (currentLanguage == "en") "English" else "Español")
        }
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = currentLanguage,
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { selectedLanguage ->
                updateLanguage(context, selectedLanguage)
                showLanguageDialog = false
            }
        )
    }
}

@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val languages = listOf("en" to "English", "es" to "Español")

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(R.string.language)) },
        text = {
            Column {
                languages.forEach { (langCode, langName) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onLanguageSelected(langCode) }
                    ) {
                        RadioButton(
                            selected = currentLanguage == langCode,
                            onClick = { onLanguageSelected(langCode) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = langName)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(android.R.string.ok))
            }
        }
    )
}

fun updateLanguage(context: Context, language: String) {
    val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("language", language)
        apply()
    }

    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    (context as Activity).recreate()
}
