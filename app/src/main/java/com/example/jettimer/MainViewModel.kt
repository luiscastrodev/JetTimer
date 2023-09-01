package com.example.jettimer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _currentTime: MutableStateFlow<Long> = MutableStateFlow(totalTime)
    val currentTime: StateFlow<Long>
        get() = _currentTime

    private val _isTimerRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean>
        get() = _isTimerRunning

    companion object {
        const val totalTime = 30 * 1000L
        const val interval = 1000L
    }
}