package rhea.group.app.ui.screens.trial.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import rhea.group.app.R

@Composable
fun TrialBackground() =
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.trial_background),
        contentDescription = "Trial Background",
        contentScale = ContentScale.FillBounds
    )