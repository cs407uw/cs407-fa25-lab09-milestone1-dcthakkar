package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // TODO: Call reset() (done)
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // the previous old acceleration a0
        val previousAccX = accX
        val previousAccY = accY

        // the new updated acceleration a1
        val newUpdatedAccX = xAcc
        val newUpdatedAccY = yAcc

        // now gonna store old previous velocity v0 before updating
        val previousOldVelX = velocityX
        val previousOldVelY = velocityY

        // the distance travel equation from lab (its the second equation)
        // keep in mind that dT = t1 - t0
        // l = v0 * dT + (1/6) * dT^2 * (3a0 + a1)
        val dt2 = dT * dT  // this is basically that dT^2
        val distanceX = previousOldVelX * dT + (1f / 6f) * dt2 * (3f * previousAccX + newUpdatedAccX)
        val distanceY = previousOldVelY * dT + (1f / 6f) * dt2 * (3f * previousAccY + newUpdatedAccY)

        // now update position
        posX += distanceX
        posY += distanceY

        // the velocity equation from the lab (its the first equation)
        // keep in mind that dT = t1 - t0
        // v1 = v0 + 0.5 * (a0 + a1) * dT
        velocityX = previousOldVelX + 0.5f * (previousAccX + newUpdatedAccX) * dT
        velocityY = previousOldVelY + 0.5f * (previousAccY + newUpdatedAccY) * dT

        // now save new acceleration as current a0
        accX = newUpdatedAccX
        accY = newUpdatedAccY

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // TODO: implement the checkBoundaries function (done)
        // (Check all 4 walls: left, right, top, bottom)
        // getting size of ball
        val maxX = backgroundWidth - ballSize
        val maxY = backgroundHeight - ballSize
        // the left side
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }
        // the right side
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }
        // the top side
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
        // the bottom side
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // TODO: implement the reset function (done)
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
        // first center the ball
        // making sure the positions are top and left of the ball
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        // now zero out the vel and acc
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f

        // now reset the first update var
        isFirstUpdate = true
    }
}