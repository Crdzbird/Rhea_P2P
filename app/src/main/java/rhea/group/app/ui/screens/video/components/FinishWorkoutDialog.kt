package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Persimmom
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.WhiteLilac

@ExperimentalComposeUiApi
@Composable
fun FinishWorkoutDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    title: String,
    description: String,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    val dialogShape = RoundedCornerShape(33.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            title = {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5.copy(
                        lineHeight = 32.sp,
                        color = Biscay
                    )
                )
            },
            text = {
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        color = Biscay
                    )
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 45.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SolidButton(
                        modifier = Modifier
                            .width(126.dp)
                            .height(62.dp)
                            .padding(horizontal = 6.dp),
                        title = stringResource(id = R.string.no),
                        shape = RoundedCornerShape(33.dp),
                        color = Persimmom,
                        icon = {},
                        onClick = {
                            onDialogStateChange?.invoke(false)
                            onDismissRequest?.invoke()
                        }
                    )
                    SolidButton(
                        modifier = Modifier
                            .width(126.dp)
                            .height(62.dp)
                            .padding(horizontal = 6.dp),
                        title = stringResource(id = R.string.yes),
                        shape = RoundedCornerShape(33.dp),
                        color = Turquoise,
                        icon = {},
                        onClick = {
                            onDialogStateChange?.invoke(false)
                            onDialogPositiveButtonClicked?.invoke()
                        }
                    )
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.Inherit,
                usePlatformDefaultWidth = false
            ),
            modifier = modifier,
            shape = dialogShape,
            backgroundColor = WhiteLilac
        )
    }
}