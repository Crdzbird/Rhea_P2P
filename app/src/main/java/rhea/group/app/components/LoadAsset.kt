package rhea.group.app.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun LoadAsset(
    modifier: Modifier = Modifier,
    asset: Int,
    description: String,
    colorFilter: ColorFilter?
) = Image(
    painter = painterResource(asset),
    contentDescription = description,
    colorFilter = colorFilter,
    modifier = modifier,
    contentScale = ContentScale.Fit
)