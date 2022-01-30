package rhea.group.app.ui.screens.trial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.ConnectivityStatus
import rhea.group.app.ui.screens.trial.components.TrialBackground
import rhea.group.app.ui.theme.KansasNew
import rhea.group.app.ui.theme.Persimmom
import rhea.group.app.ui.theme.TrialBackgroundColor
import rhea.group.app.ui.theme.White

@Composable
fun TrialScreen() {
    val context = LocalContext.current
    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Box {
            TrialBackground()
            Box {
                Surface(color = TrialBackgroundColor, modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ConnectivityStatus()
                        Image(
                            painter = painterResource(id = R.drawable.ic_rhea),
                            contentDescription = "Rhea Logo",
                            modifier = Modifier.size(width = 114.92.dp, height = 60.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.h5.copy(
                                        fontFamily = KansasNew,
                                        fontWeight = FontWeight.SemiBold,
                                        color = White
                                    ).toSpanStyle()
                                ) {
                                    append(context.getString(R.string.trial_app))
                                }
                                withStyle(
                                    style = MaterialTheme.typography.h5.copy(
                                        fontFamily = KansasNew,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Persimmom
                                    ).toSpanStyle()
                                ) {
                                    append(context.getString(R.string.trial_not_available))
                                    append(" ")
                                }
                                withStyle(
                                    style = MaterialTheme.typography.h5.copy(
                                        fontFamily = KansasNew,
                                        fontWeight = FontWeight.SemiBold,
                                        color = White
                                    ).toSpanStyle()
                                ) {
                                    append(context.getString(R.string.trial_subscription))
                                }
                            },
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 30.dp),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 7.dp),
                            text = stringResource(id = R.string.trial_detail),
                            style = MaterialTheme.typography.caption.copy(
                                color = White,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )
                        )
                    }
                }
            }
        }
    }
}