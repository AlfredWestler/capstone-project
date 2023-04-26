package com.asgh.themoviedb.presentation.modules.detail.screens.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.asgh.themoviedb.presentation.ui.components.ImageLoading
import com.asgh.themoviedb.presentation.ui.components.LoadImageFailure
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette
import kotlin.math.min

@Composable
fun ParallaxContent(
    image: String,
    headerText: String,
    content: @Composable (ScrollState) -> Unit
) {
    val scrollState = rememberScrollState()
    var palette by remember { mutableStateOf<Palette?>(null) }
    var color by remember { mutableStateOf(Color.Black) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .graphicsLayer {
                        alpha = 1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                        translationY = 0.5f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = image,
                    contentScale = ContentScale.FillBounds,
                    loading = { ImageLoading(Modifier.fillMaxSize()) },
                    failure = { LoadImageFailure(Modifier.fillMaxSize()) },
                    bitmapPalette = BitmapPalette {
                        palette = it
                        color = Color(it.dominantSwatch?.rgb ?: 0)
                    },
                )
            }
            content(scrollState)
        }
        Box(
            modifier = Modifier
                .alpha(min(1f, (scrollState.value.toFloat() / scrollState.maxValue) * 5f))
                .fillMaxWidth()
                .background(color),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = headerText,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp, top = 40.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W900,
                    color = if(color.luminance() > 0.5f) Color.Black else Color.White
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}