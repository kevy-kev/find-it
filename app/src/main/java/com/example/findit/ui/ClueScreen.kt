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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.findit.Utils
import com.example.findit.data.Clue
import com.example.findit.data.DataSource
import com.example.findit.data.FindItUiState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/*
Kevin Chou
Oregon State University CS 492
2024
 */

@SuppressLint("MissingPermission")
@Composable
fun ClueScreen(
    modifier: Modifier = Modifier,
    resetGame: () -> Unit = {},
    goHome: () -> Unit = {},
    goClueFound: () -> Unit = {},
    clue: Clue = Clue(),
    clueNumber: Int = 1,
    timer: Double = 0.0,
    pauseTimer: () -> Unit = {}
) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(LocalContext.current)

    val currentLocationRequest = CurrentLocationRequest.Builder().setMaxUpdateAgeMillis(0)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()

    var showFoundItDialog by remember {
        mutableStateOf(false)
    }
    var showHintDialog by remember {
        mutableStateOf(false)
    }

    when {
        showHintDialog -> {
            AlertDialog(onDismissRequest = { showHintDialog = false }, confirmButton = {
                Button(onClick = { showHintDialog = false }) {
                    Text(text = "Close Hint")
                }
            }, text = {
                Text(text = clue.hint2)
            })
        }
    }

    when {
        showFoundItDialog -> {
            AlertDialog(onDismissRequest = { showFoundItDialog = false }, confirmButton = {
                Button(onClick = { showFoundItDialog = false }) {
                    Text(text = "Keep Trying")
                }
            }, text = {
                Text(text = "Looks like you're not close enough!")
            })
        }
    }

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
                    "Clue #$clueNumber",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Column() {
                    Text(
                        clue.hint,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                Button(
                    onClick = { showHintDialog = true }, modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(
                        "Hint", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Timer(timer = timer)
                Button(onClick = {
                    fusedLocationClient.getCurrentLocation(currentLocationRequest, null)
                        .addOnSuccessListener { loc: Location? ->
                            if (loc != null) {
                                val calculatedDistance = Utils.calculateDistance(
                                    clue.lat, clue.lon, loc.latitude, loc.longitude
                                )
                                Log.d("FIND_IT_LOG", "Clue distance: $calculatedDistance")
                                if (calculatedDistance <= 0.1) {
                                    pauseTimer()
                                    goClueFound()
                                } else {
                                    showFoundItDialog = true
                                }
                            }
                        }
                }) {
                    Text(
                        "Found It!",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Button(onClick = {
                    resetGame()
                    goHome()
                }) {
                    Text("Quit", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview
@Composable
fun ClueScreenPreview() {
    ClueScreen(clue = DataSource.clues[1]!!)
}
