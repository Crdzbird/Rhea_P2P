package rhea.group.app.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.ui.theme.*

@Composable
fun PickerButton(
    enabled: Boolean = true,
    @DrawableRes drawable: Int,
    onClick: () -> Unit = {}
) {
    val contentDescription = LocalContext.current.resources.getResourceName(drawable)

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable(
                enabled = enabled,
                onClick = { onClick() }
            ),
        shape = CircleShape,
        backgroundColor = LinkWater,
        elevation = 0.dp
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentScale = ContentScale.Fit,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
        )
    }
}

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = 10,
    default: Int = min,
    onValueChange: (String) -> Unit = {}
) {
    val number = remember { mutableStateOf(default) }
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .align(
                    alignment = Alignment.CenterStart
                ),
            value = "${number.value}",
            onValueChange = onValueChange,
            readOnly = true,
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
            textStyle = MaterialTheme.typography.body1.copy(
                color = Biscay,
                fontWeight = FontWeight.SemiBold,
                fontFamily = KansasNew,
                lineHeight = 26.0.sp,
                textAlign = TextAlign.Start
            ),
            shape = RoundedCornerShape(50),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Number
            ),
        )
        Row(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterEnd
                )
                .fillMaxWidth(
                    fraction = .4f
                )
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PickerButton(
                drawable = R.drawable.ic_minus,
                enabled = number.value > min,
                onClick = {
                    if (number.value > min) number.value--
                    onValueChange("${number.value}")
                }
            )
            PickerButton(
                drawable = R.drawable.ic_plus,
                enabled = number.value < max,
                onClick = {
                    if (number.value < max) number.value++
                    onValueChange("${number.value}")
                }
            )
        }
    }
}