package com.example.findit.data

/*
Kevin Chou
Oregon State University CS 492
2024
 */

data class FindItUiState(
    val currentClueNumber: Int = 1,
    val currentClue: Clue = DataSource.clues.get(1)!!,
    val finished: Boolean = false,
    val timer: Double = 0.0
)
