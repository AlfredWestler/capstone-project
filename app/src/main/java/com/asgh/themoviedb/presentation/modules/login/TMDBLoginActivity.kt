package com.asgh.themoviedb.presentation.modules.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.asgh.themoviedb.commons.internet.InternetConnectionVerifier
import com.asgh.themoviedb.presentation.modules.login.screens.TMDBLoginScreen
import com.asgh.themoviedb.presentation.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TMDBLoginActivity : ComponentActivity() {
    @Inject
    lateinit var internetConnectionVerifier: InternetConnectionVerifier
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internetConnectionVerifier.observe(this) {
            it
        }
        setContent {
            TheMovieDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TMDBLoginScreen()
                }
            }
        }
    }
}