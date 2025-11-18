package com.cs407.lab09

import android.hardware.Sensor
import android.hardware.SensorEvent
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BallViewModel : ViewModel() {

    private var ball: Ball? = null
    private var lastTimestamp: Long = 0L

    // Expose the ball's position as a StateFlow
    private val _ballPosition = MutableStateFlow(Offset.Zero)
    val ballPosition: StateFlow<Offset> = _ballPosition.asStateFlow()

    /**
     * Called by the UI when the game field's size is known.
     */
    fun initBall(fieldWidth: Float, fieldHeight: Float, ballSizePx: Float) {
        if (ball == null) {
            // TODO: Initialize the ball instance (done)
            ball = Ball(backgroundWidth = fieldWidth, backgroundHeight = fieldHeight, ballSize = ballSizePx)

            // TODO: Update the StateFlow with the initial position (done)
            _ballPosition.value = Offset(ball!!.posX, ball!!.posY)
        }
    }

    /**
     * Called by the SensorEventListener in the UI.
     */
    fun onSensorDataChanged(event: SensorEvent) {
        // Ensure ball is initialized
        val currentBall = ball ?: return

        if (event.sensor.type == Sensor.TYPE_GRAVITY) {
            if (lastTimestamp != 0L) {
                // TODO: Calculate the time difference (dT) in seconds (done)
                // Hint: event.timestamp is in nanoseconds
                val NS2S = 1.0f / 1000000000.0f
                val dT = (event.timestamp - lastTimestamp) * NS2S
                // below is from the lab hints
                val xAcc = -event.values[0] * 5.0f // making ball move faster
                val yAcc = event.values[1] * 5.0f // making ball move faster

                // TODO: Update the ball's position and velocity (done)
                // Hint: The sensor's x and y-axis are inverted
                // currentBall.updatePositionAndVelocity(xAcc = ..., yAcc = ..., dT = ...)
                currentBall.updatePositionAndVelocity(xAcc = xAcc, yAcc = yAcc, dT = dT)
                // just doing a check to make sure that the ball inside the field
                currentBall.checkBoundaries()

                // TODO: Update the StateFlow to notify the UI (done)
                _ballPosition.update { Offset(currentBall.posX, currentBall.posY) }
            }

            // TODO: Update the lastTimestamp (done)
            // lastTimestamp = ...
            lastTimestamp = event.timestamp
        }
    }

    fun reset() {
        // TODO: Reset the ball's state (done)
        ball?.reset()

        // TODO: Update the StateFlow with the reset position (done)
        // ball?.let { ... }
        ball?.let { _ballPosition.value = Offset(it.posX, it.posY) }

        // TODO: Reset the lastTimestamp (done)
        lastTimestamp = 0L
    }
}