package com.asgh.themoviedb.presentation.modules.detail.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asgh.themoviedb.R

@Composable
fun RateInfoField(
    modifier: Modifier = Modifier,
    rate: Double,
    reviews: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = rate.toString(),
                modifier = Modifier
                    .padding(end = 10.dp),
                style = TextStyle(fontSize = 24.sp),
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = Color.Yellow,
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            text = "(${reviews} ${stringResource(id = R.string.reviews)})",
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp, bottom = 20.dp),
        )
    }
}