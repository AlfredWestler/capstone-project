package com.asgh.themoviedb.presentation.modules.login.screens

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asgh.themoviedb.R
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardActivity
import com.asgh.themoviedb.presentation.modules.login.TMDBLogInViewModel
import com.asgh.themoviedb.presentation.modules.login.components.TMDBImageCarousel
import com.asgh.themoviedb.presentation.ui.theme.TMDBSystemBars
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun TMDBLoginScreen(
    vm: TMDBLogInViewModel = hiltViewModel()
) {

    val density = LocalDensity.current
    val activityContext = LocalContext.current as Activity
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val color = remember { mutableStateOf(Color.Transparent) }
    val colorAnim = animateColorAsState(
        targetValue = color.value,
        animationSpec = tween(3000, 0, LinearEasing)
    )
    val textColor = animateColorAsState(
        targetValue = if(color.value.luminance() > .5f) Color.Black else Color.White,
        animationSpec = tween(3000, 0, LinearEasing)
    )

    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = {
            if(it.resultCode == RESULT_OK && it.idpResponse?.idpToken != null) {
                goToDashboard(activityContext)
            }
        }
    )

    TMDBSystemBars(color = Color.Transparent, makeTranslucent = true)
    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
        ) {
            TMDBImageCarousel {
                color.value = it
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, colorAnim.value),
                        startY = 0f,
                        endY = (screenHeight / 2).value * density.density
                    )
                )
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .align(Alignment.BottomCenter)
        ) {
            TMDBLoginOnBoarding(textColor.value) {
                vm.verifyUser(signInLauncher) {
                    goToDashboard(activityContext)
                }
            }
        }
    }
}

private fun goToDashboard(context: Activity) {
    context.startActivity(Intent(context, TMDBDashboardActivity::class.java))
    context.finish()
}

@Composable
private fun TMDBLoginOnBoarding(
    textColor: Color,
    onNextClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.on_boarding_text_1).uppercase(),
            color = textColor,
            modifier = Modifier.padding(top = 30.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.on_boarding_text_2),
            color = textColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 12.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
        Button(
            modifier = Modifier.padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            border = BorderStroke(1.dp, textColor),
            onClick = onNextClick
        ) {
            Text(
                text = stringResource(id = R.string.on_boarding_button_text),
                color = textColor,
            )
        }
    }
}