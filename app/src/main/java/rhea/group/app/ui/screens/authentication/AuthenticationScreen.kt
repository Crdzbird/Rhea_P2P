package rhea.group.app.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import rhea.group.app.R
import rhea.group.app.components.ConnectivityStatus
import rhea.group.app.ui.screens.authentication.components.AuthGroup
import rhea.group.app.ui.screens.authentication.components.Background
import rhea.group.app.ui.screens.authentication.viewModel.AuthenticationViewModel
import rhea.group.app.ui.theme.BackgroundColor
import rhea.group.app.ui.theme.Botticelli
import rhea.group.app.ui.theme.FtpPolar

@Composable
fun AuthenticationScreen(authViewModel: AuthenticationViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Box {
            Background()
            Box {
                Surface(color = BackgroundColor, modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ConnectivityStatus()
                        Image(
                            painter = painterResource(id = R.drawable.ic_rhea_letters),
                            contentDescription = "Rhea Logo",
                            modifier = Modifier.size(width = 154.92.dp, height = 100.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 37.0.dp),
                            text = stringResource(id = R.string.title_description),
                            style = MaterialTheme.typography.h3
                        )
                        AuthGroup(
                            onGoogleClick = {},
                            onFacebookClick = {},
                            onCredentialsClick = {
                                authViewModel.navigateToCredentials()
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.caption.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FtpPolar
                                    ).toSpanStyle()
                                ) {
                                    append(context.getString(R.string.trying_join_rhea))
                                    append(" ")
                                }
                                withStyle(
                                    style = MaterialTheme.typography.caption.copy(
                                        color = Botticelli,
                                        fontFamily = FtpPolar
                                    ).toSpanStyle()
                                ) {
                                    append(context.getString(R.string.trying_join_rhea_description))
                                }
                            },
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(horizontal = 50.dp),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}