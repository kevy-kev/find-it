package com.example.findit.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.findit.Utils
import com.example.findit.data.Clue
import com.example.findit.data.DataSource
import com.example.findit.data.FindItUiState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/*
Kevin Chou
Oregon State University CS 492
2024
 */


@Composable
fun ClueFoundScreen(
    modifier: Modifier = Modifier,
    clue: Clue = Clue(),
    clueNumber: Int = 1,
    setClue: (number: Int) -> Unit,
    goClue: () -> Unit = {},
    resetGame: () -> Unit = {},
    goHome: () -> Unit = {},
    timer: Double = 0.0,
    startTimer: () -> Unit = {}
) {
    val isFinalClue = clueNumber == DataSource.clues.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
//        Container
        Column(
            Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    if (isFinalClue) "Congratulations!" else "Good Job!",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                if (isFinalClue) {
                    Column() {
                        Text(
                            "You have found all of the locations!",
                            style = MaterialTheme.typography.bodyLarge.plus(TextStyle(fontWeight = FontWeight.Bold)),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }

                Column() {
                    Text(
                        clue.info,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Timer(timer = timer)
                Button(onClick = {
                    if (isFinalClue) {
                        goHome()
                        resetGame()
                    } else {
                        goClue()
                        setClue(clueNumber + 1)
                        startTimer()
                    }
                }) {
                    Text(
                        if (isFinalClue) "Home" else "Continue", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ClueFoundScreenPreview() {
    ClueFoundScreen(clue = DataSource.clues[1]!!, setClue = {})
}

@Preview
@Composable
fun FinalClueFoundScreenPreview() {
    ClueFoundScreen(clue = DataSource.clues[2]!!, clueNumber = 2, setClue = {})
}
