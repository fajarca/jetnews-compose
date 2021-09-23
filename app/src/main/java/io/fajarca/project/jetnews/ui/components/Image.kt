package io.fajarca.project.jetnews.ui.components

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RemoteImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        painter = rememberImagePainter(data = url, builder = {
            crossfade(true)
            placeholder(ColorDrawable(Color.LTGRAY))
        }),
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.Center,
        contentScale = contentScale
    )
}