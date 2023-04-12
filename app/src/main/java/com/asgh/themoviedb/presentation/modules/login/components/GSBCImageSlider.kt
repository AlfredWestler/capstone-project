package com.asgh.themoviedb.presentation.modules.login.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.palette.graphics.Palette
import com.asgh.themoviedb.R
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette
import kotlinx.coroutines.delay
import java.util.*

val imageList = listOf(
    "https://firebasestorage.googleapis.com/v0/b/tmdbproject-75852.appspot.com/o/darth_onboarding.jpeg?alt=media&token=0d4573d5-a700-45b2-aea4-0c31a50b033f",
    "https://firebasestorage.googleapis.com/v0/b/tmdbproject-75852.appspot.com/o/ironman_onboarding.webp?alt=media&token=54e9b320-5ed0-4bc1-9b3e-08ab75ee03a9",
    "https://firebasestorage.googleapis.com/v0/b/tmdbproject-75852.appspot.com/o/stranger_onboarding.jpeg?alt=media&token=64351d0e-804e-4070-9ead-7b71223a24f7",
    "https://firebasestorage.googleapis.com/v0/b/tmdbproject-75852.appspot.com/o/lotr_onboarding.jpeg?alt=media&token=8c26a09d-dacb-4673-9047-d118197a5d28",
    "https://firebasestorage.googleapis.com/v0/b/tmdbproject-75852.appspot.com/o/titanic_onboarding.png?alt=media&token=90558b22-de01-4daa-bebf-9131167911ff",
)

@Composable
fun TMDBImageCarousel(
    currentImageColor: (Color) -> Unit = {}
) {
    val initial = remember { mutableStateOf(true) }
    var maintainPosition by remember { mutableStateOf(1) }
    var visible by remember { mutableStateOf(true) }
    var palette by remember { mutableStateOf<Palette?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            visible = false //solo se muestra el previo ironman
            delay(5000) // visible por 5s
            initial.value = false
            visible = true // iron man se queda en segundo plano y se muestra dartvader con fade in
            delay(1000) // delay de la animacion
            currentImageColor(Color(palette?.dominantSwatch?.rgb ?: 0))
            delay(2000)
            visible = false
            Collections.rotate(imageList, -1) // se rota la lista
            maintainPosition = 0
            delay(1000)
            maintainPosition = 1
        }
    }
    Box (modifier = Modifier.fillMaxSize()) {
        GlideImage(
            imageModel = imageList[0],
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            bitmapPalette = BitmapPalette {
                if(initial.value) {
                    palette = it
                    currentImageColor(Color(it.dominantSwatch?.rgb ?: 0))
                }
            },
            loading = {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_1),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(3000, 0, LinearEasing)),
            exit = fadeOut(animationSpec = tween(1000, 0, LinearEasing))
        ) {
            GlideImage(
                imageModel = imageList[maintainPosition],
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                bitmapPalette = BitmapPalette {
                    if(!initial.value) { palette = it }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageSlider() { TMDBImageCarousel() }

