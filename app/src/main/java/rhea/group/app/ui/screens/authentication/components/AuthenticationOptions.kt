package rhea.group.app.ui.screens.authentication.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.components.LoadAsset
import rhea.group.app.components.SolidButton
import rhea.group.app.ui.theme.Black
import rhea.group.app.ui.theme.PictonBlue
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun AuthGroup(
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onCredentialsClick: () -> Unit = {}
    ) = Column {
    SolidButton(
        color = PictonBlue,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 25.dp),
        shape = RoundedCornerShape(50),
        hasIcon = true,
        icon = {
            LoadAsset(
                modifier = Modifier
                    .size(width = 16.3.dp, height = 16.56.dp),
                asset = R.drawable.ic_google,
                description = stringResource(id = R.string.google),
                colorFilter = ColorFilter.tint(White)
            )
        },
        title = stringResource(id = R.string.google),
        onClick = onGoogleClick
    )
    Spacer(modifier = Modifier.height(10.dp))
    SolidButton(
        color = Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 25.dp),
        hasIcon = true,
        shape = RoundedCornerShape(50),
        icon = {
            LoadAsset(
                modifier = Modifier
                    .size(width = 16.3.dp, height = 16.56.dp),
                asset = R.drawable.ic_apple,
                description = stringResource(id = R.string.apple),
                colorFilter = ColorFilter.tint(White)
            )
        },
        title = stringResource(id = R.string.apple),
        onClick = onFacebookClick
    )
    Spacer(modifier = Modifier.height(10.dp))
    SolidButton(
        color = Turquoise,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 25.dp),
        hasIcon = true,
        icon = {
            LoadAsset(
                modifier = Modifier
                    .size(width = 16.3.dp, height = 16.56.dp),
                asset = R.drawable.ic_email,
                description = stringResource(id = R.string.email),
                colorFilter = ColorFilter.tint(White)
            )
        },
        shape = RoundedCornerShape(50),
        title = stringResource(id = R.string.email),
        onClick = onCredentialsClick
    )
}