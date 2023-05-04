package com.asgh.themoviedb.presentation.modules.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.navArgs
import com.asgh.themoviedb.commons.converters.fromJson
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.presentation.modules.detail.screens.TMDBDetailScreen
import com.asgh.themoviedb.presentation.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBDetailActivity : ComponentActivity() {

    private val vm: TMDBDetailVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val safeArgs by navArgs<TMDBDetailActivityArgs>()
//        val movie = safeArgs.movie.fromJson(TMDBMovie::class.java) ?: TMDBMovie() // store this info in vm
        val movie = safeArgs.movie.toInt()

//        vm.setSelectedMovie(movie)
        vm.getMovieById(movie)

        setContent {
            TheMovieDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TMDBDetailScreen(vm)
                }
            }
        }
    }
}