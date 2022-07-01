package com.example.coroutinestart

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.lang.RuntimeException


class MainViewModel: ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d(LOG_TAG, "Coroutine started")
            val before = System.currentTimeMillis()
            var count = 0L
            for (i in 0 until 100_000_000) {
                for (j in 0 until 100) {
//                    if (isActive) {
//                        ++count
//                    } else {
//                        throw CancellationException()
//                    }
                    ensureActive()
                    ++count
                }
            }
            Log.d(LOG_TAG, "Work time ${System.currentTimeMillis() - before}")
        }
        job.invokeOnCompletion {
            Log.d(LOG_TAG, "Coroutine finished | $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }

    companion object {

        private const val LOG_TAG = "MainViewModelTag"
    }
}