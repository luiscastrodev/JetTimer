package com.example.jettimer

import android.content.res.Configuration
import android.util.FloatMath
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettimer.MainViewModel.Companion.totalTime
import com.example.jettimer.ui.theme.JetTimerTheme
import com.example.jettimer.ui.theme.blue200
import com.example.jettimer.ui.theme.blue400
import com.example.jettimer.ui.theme.blue500
import com.example.jettimer.ui.theme.card

const val TIMER_RADIUS = 300f

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Timer(
    currentTime: Long,
    isRunning: Boolean,
    onStart: () -> Unit,
    onRestart: () -> Unit
) {

    val transition = updateTransition(targetState = currentTime, label = null)
    val tran by transition.animateFloat(transitionSpec = {
        tween(1000, easing =  FastOutLinearInEasing)
    }, label = ""){
        timerLeft  ->
        if(timerLeft <0){
            350f
        }else{
            360f-((360f / totalTime) * (totalTime - timerLeft))
        }
    }
    
    val progress by animateFloatAsState(targetValue = if(isRunning) tran else 0f, label = "")

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

        JetTimerProgressIndicator(progress = progress, currentTime = currentTime )

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

        CircularIndicator(progress = progress )

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
    Surface(color = MaterialTheme.colorScheme.background) {
        val stroke = with(LocalDensity.current) {
            Stroke(
                width = 30.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            onDraw = {
                inset(
                    horizontal = (size.width / 2) - TIMER_RADIUS,
                    vertical = (size.height / 2) - TIMER_RADIUS
                ) {
                    val gradient = Brush.linearGradient(
                        listOf(blue500, blue200, blue400)
                    )
                    drawBackground(card)
                    drawProgressIndicator(
                        brush = gradient,
                        progress ,
                        stroke
                    )
                }
            }
        )
    }
}

fun DrawScope.drawBackground(
    color: Color
) {
    drawCircle(
        color = color,
        radius = TIMER_RADIUS,
        center = center
    )
}

fun DrawScope.drawProgressIndicator(
    brush: Brush,
    progress: Float,
    stroke: Stroke
) {
    val innerRadius = (size.minDimension - stroke.width) / 2
    val halfSize = size / 2.0f
    val topLeft = Offset(
        x = halfSize.width - innerRadius,
        y = halfSize.height - innerRadius
    )

    val size = Size(
        innerRadius * 2,
        height = innerRadius * 2
    )

    drawArc(
        brush = brush,
        startAngle = 270f,
        sweepAngle = progress,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = stroke,
        blendMode = BlendMode.SrcIn
    )
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