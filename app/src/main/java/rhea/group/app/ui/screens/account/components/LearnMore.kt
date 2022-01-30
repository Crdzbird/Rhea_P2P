package rhea.group.app.ui.screens.account.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Salmon
import rhea.group.app.ui.theme.Turquoise

@Composable
fun LearnMore(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rhea),
            contentDescription = stringResource(id = R.string.rhea),
            modifier = Modifier
                .size(width = 60.01.dp, height = 45.41.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.h3.copy(
                        color = Turquoise
                    ).toSpanStyle()
                ) {
                    append(context.getString(R.string.active_recovery))
                    append(" ")
                }
                withStyle(
                    style = MaterialTheme.typography.h3.copy(
                        color = Biscay
                    ).toSpanStyle()
                ) {
                    append(context.getString(R.string.for_a))
                    append(" ")
                }
                withStyle(
                    style = MaterialTheme.typography.h3.copy(
                        color = Salmon
                    ).toSpanStyle()
                ) {
                    append(context.getString(R.string.healthy_brain))
                    append(" ")
                }
            },
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(
                horizontal = 56.dp,
            ),
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        SolidButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp, vertical = 20.dp)
                .height(45.dp),
            color = Turquoise,
            shape = RoundedCornerShape(33.dp),
            icon = {},
            title = stringResource(id = R.string.learn_more),
            onClick = {
                uriHandler.openUri(uri = "https://getrhea.com")
            }
        )
    }
}