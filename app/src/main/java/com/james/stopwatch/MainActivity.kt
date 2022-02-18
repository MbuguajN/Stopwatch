package com.james.stopwatch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : Activity() {
    private var sec = 0
    private var isRunning = false
    private var wasrunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            sec = savedInstanceState.getInt("seconds")
            isRunning = savedInstanceState.getBoolean("running")
            wasrunning = savedInstanceState.getBoolean("wasRunning")
        }
        runningTimer()
    }

    public override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ) {
        savedInstanceState.putInt("seconds", sec)
        savedInstanceState.putBoolean("running", isRunning)
        savedInstanceState.putBoolean("wasRunning", wasrunning)
    }

    override fun onPause() {
        super.onPause()
        wasrunning = isRunning
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        if (wasrunning) {
            isRunning = true
        }
    }

    fun onClickStart() {
        isRunning = true
    }

    fun onClickStop() {
        isRunning = false
    }

    fun onClickReset() {
        isRunning = false
        sec = 0
    }

    private fun runningTimer() {
        val tView = findViewById<View>(R.id.time_view) as TextView
        val handle = Handler()
        handle.post(object : Runnable {
            override fun run() {
                val hrs = sec / 3600
                val mins = sec % 3600 / 60
                val secs = sec % 60
                val timeT =
                    String.format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs, mins, secs)
                tView.text = timeT
                if (isRunning) {
                    sec++
                }
                handle.postDelayed(this, 1000)
            }
        })
    }
}