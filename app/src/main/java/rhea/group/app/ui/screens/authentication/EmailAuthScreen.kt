package rhea.group.app.ui.screens.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhea.group.app.R
import rhea.group.app.components.ShowSnackbar
import rhea.group.app.components.SolidButton
import rhea.group.app.models.ErrorResponse
import rhea.group.app.models.NetworkState
import rhea.group.app.models.onError
import rhea.group.app.models.onSuccess
import rhea.group.app.ui.screens.authentication.viewModel.EmailAuthViewModel
import rhea.group.app.ui.theme.*

@Composable
fun EmailAuthScreen(emailAuthViewModel: EmailAuthViewModel = hiltViewModel()) {
    val authResponse = emailAuthViewModel.authResponse.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val networkState: NetworkState by emailAuthViewModel.authenticationNetworkState
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        networkState.onSuccess {
            emailAuthViewModel.profileFromEmail()
        }.onError {
            authResponse.value?.let {
                LaunchedEffect((it as ErrorResponse).message) {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
            }
            ShowSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.Bottom)
                    .padding(bottom = 10.dp)
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val credentialsValid = remember { mutableStateOf(false) }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val passwordVisibility = remember { mutableStateOf(true) }
                Image(
                    painter = painterResource(id = R.drawable.ic_rhea),
                    contentDescription = "Rhea Logo",
                    modifier = Modifier
                        .size(width = 60.01.dp, height = 45.41.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.5.dp, end = 22.5.dp, top = 32.0.dp),
                    text = stringResource(id = R.string.welcome_back),
                    style = MaterialTheme.typography.h3.copy(color = Biscay),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 50.0.dp, top = 38.0.dp),
                    text = stringResource(id = R.string.email_hint),
                    style = MaterialTheme.typography.body2
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Biscay,
                        backgroundColor = White,
                        cursorColor = Black,
                        disabledLabelColor = Hoki,
                        focusedLabelColor = White,
                        unfocusedLabelColor = White,
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        disabledIndicatorColor = Transparent
                    ),
//                           isError = !EmailHelper().isValidEmail(emailState.value.text),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = Biscay,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = KansasNew,
                        lineHeight = 26.0.sp
                    ),
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 50.0.dp, top = 20.0.dp),
                    text = stringResource(id = R.string.password_hint),
                    style = MaterialTheme.typography.body2
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Image(
                                modifier = Modifier.size(width = 23.0.dp, height = 18.0.dp),
                                painter = if (passwordVisibility.value) painterResource(id = R.drawable.ic_eye_open) else painterResource(
                                    id = R.drawable.ic_eye_close
                                ),
                                contentDescription = "visibility",
                                contentScale = ContentScale.Fit,
                                colorFilter = if (passwordState.value.text.isBlank()) ColorFilter.tint(
                                    Botticelli
                                ) else ColorFilter.tint(Biscay)
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = White,
                        cursorColor = Black,
                        disabledLabelColor = Hoki,
                        focusedLabelColor = White,
                        unfocusedLabelColor = White,
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        disabledIndicatorColor = Transparent
                    ),
//                            isError = passwordState.value.text.isEmpty(),
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = Biscay,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = KansasNew,
                        lineHeight = 26.0.sp
                    ),
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
                )
            }
        }
        SolidButton(
            networkState = networkState,
            enabled = true,
            color = Turquoise,
            disabledColor = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (credentialsValid.value) Turquoise else Botticelli
                    ),
                    shape = RoundedCornerShape(50)
                ),
            icon = {},
            titleColor = White,
            titleDisabledColor = Botticelli,
            shape = RoundedCornerShape(50),
            title = stringResource(id = R.string.login),
            onClick = {
                scope.launch(Dispatchers.IO) {
                    emailAuthViewModel.authenticate(
                        email = "jack.chorley+sleep@me.com",
                        password = "MySecurePassword1!"
                    )
                }
            }
        )
    }
}