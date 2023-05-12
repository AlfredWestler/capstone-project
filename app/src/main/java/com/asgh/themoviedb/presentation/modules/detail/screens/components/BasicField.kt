package com.asgh.themoviedb.presentation.modules.detail.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicField(
    modifier: Modifier = Modifier,
    title: String,
    contentText: String,
    isLoading: Boolean
) {
    Column(modifier) {
        if(isLoading) {
            Box(Modifier.fillMaxWidth().height(20.dp))
            Box(Modifier.fillMaxWidth().height(20.dp).padding(top = 4.dp))
        } else {
            Text(text = title)
            Text(
                text = contentText,
                style = TextStyle(fontSize = 24.sp)
            )
        }
    }
}