package com.example.findit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.floor

fun convertToString(seconds: Double): String {
    var _seconds = seconds
    var h = 0
    var m = 0
    var s = 0

    h = floor(_seconds / 3600).toInt()
    _seconds -= h * 3600

    m = floor(_seconds / 60).toInt()
    _seconds -= m * 60

    s = _seconds.toInt()

    return "$h:$m:$s"
}

@Composable
fun Timer(timer: Double) {
    Text(
        "Total time",
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        convertToString(timer),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}