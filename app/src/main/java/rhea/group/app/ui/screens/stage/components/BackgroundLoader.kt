package rhea.group.app.ui.screens.stage.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.drawable.CrossfadeDrawable
import coil.size.Scale
import com.valentinilk.shimmer.shimmer
import rhea.group.app.R
import androidx.compose.foundation.Image as BaseImage

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BackgroundLoader(
    modifier: Modifier = Modifier,
    data: String?,
    contentDescription: String? = null,
    fadeIn: Boolean = false,
    fadeInDurationMs: Int = CrossfadeDrawable.DEFAULT_DURATION,
    contentScale: ContentScale = ContentScale.FillBounds,
    enablePlaceHolder: Boolean = false
) {
    val painter = rememberImagePainter(data = data) {
        data(data)
        crossfade(fadeIn)
        crossfade(fadeInDurationMs)
        scale(Scale.FILL)
        error(R.drawable.ic_rhea)
    }

    val enableShimmer = if (enablePlaceHolder)
        painter.state is ImagePainter.State.Loading
    else false

    val isError = painter.state is ImagePainter.State.Error

    BaseImage(
        modifier =
        if (enableShimmer) modifier.shimmer() else modifier,
        painter = painter,
        contentDescription = contentDescription,
        contentScale = if (isError) ContentScale.None else contentScale
    )
}