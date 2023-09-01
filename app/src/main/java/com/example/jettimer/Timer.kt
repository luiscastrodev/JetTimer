package com.example.jettimer

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettimer.ui.theme.JetTimerTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Timer(
    currentTime: Long,
    isRunning: Boolean,
    onStart: () -> Unit,
    onRestart: () -> Unit
) {
    Box(
        modifier =
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = { onStart.invoke() }) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = { onRestart() }) {
                Text(text = "Restart")
            }
        }

        AnimatedContent(
            targetState = currentTime,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                } else {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                }.using(
                    sizeTransform = SizeTransform(clip = false)
                )
            }, label = ""
        ) { time ->
            Text(
                text = getFormattedTime(time),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun JetTimerProgressIndicator(
    progress: Float,
    currentTime: Long
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        AnimatedContent(
            targetState = currentTime,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                } else {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                }.using(
                    sizeTransform = SizeTransform(clip = false)
                )
            }, label = ""
        ) { time ->
            Text(
                text = getFormattedTime(time),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
private fun CircularIndicator(progress: Float) {
    Surface(color =  MaterialTheme.colorScheme.background) {
        val stroke = with(LocalDensity.current){
        }
    }
}

@Preview(
    name = "Light mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true, name = "Dark Mode"
)
@Composable
private fun FunctionName() {
    JetTimerTheme {
        Surface {
            Timer(currentTime = 10, isRunning = true, onStart = { /*TODO*/ }) {

            }
        }
    }
}