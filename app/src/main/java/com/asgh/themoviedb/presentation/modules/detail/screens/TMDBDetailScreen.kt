package com.asgh.themoviedb.presentation.modules.detail.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asgh.themoviedb.R
import com.asgh.themoviedb.presentation.modules.detail.TMDBDetailVM
import com.asgh.themoviedb.presentation.modules.detail.screens.components.BasicField
import com.asgh.themoviedb.presentation.modules.detail.screens.components.ParallaxContent
import com.asgh.themoviedb.presentation.modules.detail.screens.components.RateInfoField
import com.asgh.themoviedb.presentation.modules.detail.screens.components.RelatedMoviesComponent
import com.asgh.themoviedb.presentation.ui.theme.TMDBSystemBars
import kotlinx.coroutines.launch

@Composable
fun TMDBDetailScreen(
    vm: TMDBDetailVM,
) {

    val coroutineScope = rememberCoroutineScope()
    TMDBSystemBars(color = Color.Transparent, makeTranslucent = true)

    ParallaxContent(
        image = vm.selectedMovie.value.backdropPath,
        headerText = vm.selectedMovie.value.title
    ) { scrollState ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            BasicField(
                Modifier.fillMaxWidth(),
                title = "${stringResource(id = R.string.original_title)}:",
                contentText = vm.selectedMovie.value.originalTitle
            )
            BasicField(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                title = "${stringResource(id = R.string.genres)}:",
                contentText = vm.movieGenres.value
            )
            Text(
                text = "${vm.selectedMovie.value.releaseDate} • ${vm.selectedMovie.value.originalLanguage}",
                modifier = Modifier.padding(top = 10.dp),
            )
            Text(
                text = vm.selectedMovie.value.overview,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(top = 10.dp),
                style = TextStyle(fontSize = 24.sp),
            )
            RateInfoField(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                rate = vm.selectedMovie.value.voteAverage,
                reviews = vm.selectedMovie.value.voteCount
            )
            RelatedMoviesComponent(
                Modifier.fillMaxWidth().padding(top = 10.dp),
                relatedMovies = vm.relatedMovies
            ) {
                coroutineScope.launch { scrollState.animateScrollTo(0) }
                vm.setSelectedMovie(it)
            }
        }
    }
}