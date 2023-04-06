package com.asgh.themoviedb.presentation.modules.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.asgh.themoviedb.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TMDBLogInViewModel @Inject constructor(): ViewModel() {

    fun verifyUser(
        signInLauncher: ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult>,
        verifiedUser: () -> Unit
    ) {
        if(Firebase.auth.currentUser == null) {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_tmdb)
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                    )
                )
                .build()
            signInLauncher.launch(signInIntent)
        } else {
            verifiedUser()
        }
    }
}