package rhea.group.app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rhea.group.app.models.NetworkState
import rhea.group.app.models.onError
import rhea.group.app.models.onLoading
import rhea.group.app.models.onSuccess
import rhea.group.app.ui.theme.Transparent
import rhea.group.app.ui.theme.White

@Composable
fun SolidButton(
    modifier: Modifier = Modifier,
    networkState: NetworkState? = null,
    enabled: Boolean? = true,
    color: Color,
    disabledColor: Color? = Transparent,
    shape: RoundedCornerShape,
    icon: @Composable () -> Unit?,
    hasIcon: Boolean = false,
    title: String,
    titleColor: Color? = White,
    titleDisabledColor: Color? = White,
    onClick: () -> Unit = {}
) {
    var isLoading = false
    networkState?.let {
        it.onLoading {
            isLoading = true
        }.onSuccess {
            isLoading = false
        }.onError {
            isLoading = false
        }
    }
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            disabledContentColor = disabledColor ?: Transparent,
            disabledBackgroundColor = disabledColor ?: Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        shape = shape,
        onClick = if (isLoading) ({}) else onClick,
        enabled = if (isLoading) false else enabled ?: true,
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            if (hasIcon) {
                icon()
                Spacer(modifier = Modifier.width(13.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.button.copy(
                    color = if (enabled != false) titleColor ?: White else titleDisabledColor
                        ?: Transparent
                )
            )
        }
    }
}