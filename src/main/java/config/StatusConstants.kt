package config

import com.ctre.phoenix.led.Animation
import com.ctre.phoenix.led.ColorFlowAnimation
import com.ctre.phoenix.led.RainbowAnimation

object StatusConstants {
    const val CANDLE_ID = 15
    private const val LED_COUNTS = 25

    val disabledStatus = StatusState(RainbowAnimation(0.7, 0.5, LED_COUNTS), RGB(255, 192, 203))
    val intakeStatus = StatusState(ColorFlowAnimation(255, 0, 0), RGB(0, 255, 255))
}

data class StatusState(val anim: Animation, val rgb: RGB)

data class RGB(val r: Int, val g: Int, val b: Int)