package com.example.findit.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.data.Clue
import com.example.findit.data.DataSource
import com.example.findit.data.FindItUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
Kevin Chou
Oregon State University CS 492
2024
 */

class FindItViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FindItUiState())
    val uiState: StateFlow<FindItUiState> = _uiState.asStateFlow()

    fun reset() {
        _uiState.update { currentState ->
            currentState.copy(
                currentClue = DataSource.clues[1]!!, finished = false, currentClueNumber = 1
            )
        }
        resetTimer()
    }

    fun setCurrentClue(number: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentClue = DataSource.clues[number] ?: Clue(), currentClueNumber = number
            )
        }
    }

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { currentState ->
                    currentState.copy(
                        timer = currentState.timer + 1
                    )
                }
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        timerJob?.cancel()
        _uiState.update { currentState -> currentState.copy(timer = 0.0) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}