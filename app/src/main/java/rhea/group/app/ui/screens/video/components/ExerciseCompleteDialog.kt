package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import rhea.group.app.R
import rhea.group.app.ui.theme.Hoki
import rhea.group.app.ui.theme.PictonBlue
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.WhiteLilacOpacity

@Composable
fun ExerciseCompletedDialog(
    modifier: Modifier = Modifier, openDialog: MutableState<Boolean>,
    onRestartClick: () -> Unit, onFinishClick: () -> Unit
) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(Color.White)
            ) {

                //.......................................................................
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_check),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = Turquoise
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.exercise_completed),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = R.string.session_completed),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(
                            lineHeight = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                //.......................................................................
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(WhiteLilacOpacity),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        openDialog.value = false
                        onRestartClick()
                    }) {

                        Text(
                            stringResource(id = R.string.go_again),
                            fontWeight = FontWeight.Bold,
                            color = PictonBlue,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        openDialog.value = false
                        onFinishClick()
                    }) {
                        Text(
                            stringResource(id = R.string.finish),
                            fontWeight = FontWeight.ExtraBold,
                            color = Hoki,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}