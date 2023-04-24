package com.asgh.themoviedb.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ImageLoading(
    modifier: Modifier = Modifier,
    thickness: Dp = 4.dp
) {
    val shimmerColor = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Red
    )
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = modifier
            .border(
                width = thickness,
                brush = Brush.linearGradient(
                    colors = shimmerColor,
                    start = Offset.Zero,
                    end = Offset(x = rotation, y = rotation)
                ),
                shape = RoundedCornerShape(10.dp)
            )
    )
}