package org.avmedia.gshockGoogleSync.ui.others

import AppTextExtraLarge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.data.repository.TranslateRepository
import org.avmedia.gshockGoogleSync.ui.actions.ActionRunner

@Composable
fun RunActionsScreen(
    repository: GShockRepository,
    translateApi: TranslateRepository
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AppTextExtraLarge(
            text = translateApi.stringResource(
                context = LocalContext.current,
                id = R.string.running_actions
            ),
            fontWeight = FontWeight.Bold // Adjust as needed
        )

        ActionRunner(context = LocalContext.current, api = repository)
    }
}
