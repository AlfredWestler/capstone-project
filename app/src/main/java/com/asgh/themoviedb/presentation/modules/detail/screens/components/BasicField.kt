package com.asgh.themoviedb.presentation.modules.detail.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun BasicField(
    modifier: Modifier = Modifier,
    title: String,
    contentText: String
) {
    Column(modifier) {
        Text(text = title)
        Text(
            text = contentText,
            style = TextStyle(fontSize = 24.sp)
        )
    }
}