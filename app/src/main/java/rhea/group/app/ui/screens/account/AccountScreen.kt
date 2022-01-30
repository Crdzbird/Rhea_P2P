package rhea.group.app.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.models.Profile
import rhea.group.app.models.ViewState
import rhea.group.app.ui.screens.account.components.LearnMore
import rhea.group.app.ui.screens.account.viewModel.AccountViewModel
import rhea.group.app.ui.theme.*

@Composable
fun AccountScreen(accountViewModel: AccountViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    val accountViewState = accountViewModel.accountViewState.collectAsState().value
    LaunchedEffect(key1 = true) {
        accountViewModel.initDataStore()
    }
    var emailState by remember {
        mutableStateOf(TextFieldValue())
    }
    when (accountViewState) {
        is ViewState.Success -> {
            emailState = TextFieldValue((accountViewState.success as Profile).firstName)
        }
        else -> {}
    }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.0.dp, top = 38.0.dp),
                text = stringResource(id = R.string.first_name),
                style = MaterialTheme.typography.body2
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                value = emailState,
                onValueChange = {
                    emailState = it
                    coroutineScope.launch {
                        accountViewModel.profile = accountViewModel.profile.copy(
                            firstName = it.text
                        )
                        accountViewModel.updateProfile(accountViewModel.profile)
                    }
                },
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.0.dp, top = 38.0.dp),
                text = stringResource(id = R.string.last_name),
                style = MaterialTheme.typography.body2
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                value = "",//emailState.value,
                onValueChange = { /*emailState.value = it */ },
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
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.0.dp, top = 38.0.dp),
                text = stringResource(id = R.string.email_hint),
                style = MaterialTheme.typography.body2
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                value = "",//emailState.value,
                onValueChange = { /*emailState.value = it*/ },
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
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.0.dp, top = 20.0.dp),
                text = stringResource(id = R.string.password_hint),
                style = MaterialTheme.typography.body2
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.0.dp, end = 25.0.dp, top = 10.0.dp),
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                trailingIcon = {
                    IconButton(onClick = {
                        //passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Image(
                            modifier = Modifier.size(width = 23.0.dp, height = 18.0.dp),
                            painter =
                            //if (passwordVisibility.value)
                            //  painterResource(id = R.drawable.ic_eye_open)
                            //else
                            painterResource(id = R.drawable.ic_eye_close),
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
                visualTransformation =
//                if (passwordVisibility.value)
//                    PasswordVisualTransformation()
//                else
                VisualTransformation.None
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.0.dp, top = 38.0.dp),
                text = stringResource(id = R.string.date_of_birth),
                style = MaterialTheme.typography.body2
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.0.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextField(
                    modifier = Modifier.fillParentMaxWidth(
                        fraction = 0.25f
                    ),
                    value = "",//emailState.value,
                    onValueChange = { /*emailState.value = it*/ },
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
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
                TextField(
                    modifier = Modifier.fillParentMaxWidth(
                        fraction = 0.25f
                    ),
                    value = "",//emailState.value,
                    onValueChange = { /*emailState.value = it*/ },
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
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
                TextField(
                    modifier = Modifier.fillParentMaxWidth(
                        fraction = 0.25f
                    ),
                    value = "",//emailState.value,
                    onValueChange = { /*emailState.value = it*/ },
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
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
            }
        }
        item {
            SolidButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 40.dp)
                    .height(60.dp),
                color = Turquoise,
                shape = RoundedCornerShape(33.dp),
                icon = {},
                title = stringResource(id = R.string.update_my_account),
                onClick = {}
            )
        }
        item {
            LearnMore(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(top = 50.dp)
            )
        }
    }
}