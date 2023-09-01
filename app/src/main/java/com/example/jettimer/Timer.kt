package com.example.jettimer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettimer.ui.theme.JetTimerTheme


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

        Text(text = getFormattedTime(currentTime))
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