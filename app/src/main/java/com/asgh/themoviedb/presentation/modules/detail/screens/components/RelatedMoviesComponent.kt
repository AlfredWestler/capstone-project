package com.asgh.themoviedb.presentation.modules.detail.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.asgh.themoviedb.data.mapper.toMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.presentation.ui.components.ImageLoading
import com.asgh.themoviedb.presentation.ui.components.LoadImageFailure
import com.asgh.themoviedb.presentation.ui.components.ShimmerAnimation
import com.example.local.relation.TMDBMoviesInGenre
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RelatedMoviesComponent(
    modifier: Modifier = Modifier,
    relatedMovies: List<TMDBMoviesInGenre>,
    isLoading: Boolean,
    onMovieClick: (TMDBMovie) -> Unit
) {
    Column(modifier) {
        relatedMovies.forEachIndexed { index, moviesInGenre ->
            if(isLoading) {
                ShimmerAnimation {
                    Box(
                        Modifier
                            .width(40.dp)
                            .height(20.dp)
                            .background(it))
                    LazyRow(Modifier.fillMaxWidth()) {
                        items(5) {_ ->
                            Box(
                                Modifier
                                    .padding(end = 8.dp)
                                    .width(150.dp)
                                    .height(200.dp)
                                    .background(it, RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = when(index) {
                        0 -> "Podría interesarte del genero ${moviesInGenre.genre.genreName}"
                        1 -> "Seleccionado para ti del genero ${moviesInGenre.genre.genreName}"
                        2 -> "No te pierdas del genero ${moviesInGenre.genre.genreName}"
                        else -> "Nuevo para descubrir del genero ${moviesInGenre.genre.genreName}"
                    }
                )
                LazyRow(Modifier.fillMaxWidth()) {
                    items(moviesInGenre.movies) {
                        RelatedMovieCard(
                            image = it.posterPath.orEmpty(),
                            onMovieClick = { onMovieClick(it.toMovie()) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RelatedMovieCard(
    image: String,
    onMovieClick: () -> Unit
) {
    Box(
        Modifier
            .padding(end = 8.dp)
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onMovieClick)
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = image,
            contentScale = ContentScale.FillBounds,
            loading = { ImageLoading(Modifier.fillMaxSize()) },
            failure = { LoadImageFailure(Modifier.fillMaxSize()) },
        )
    }
}