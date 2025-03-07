package frc.robot.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration
import config.State

//data class State (var state: Boolean, val color: CANdle.LEDStripType, val animation: Animation)
var candle = CANdle(0)
var config = CANdleConfiguration()
object CANdleHandler {
    fun setState(state: State){
        config.stripType = state.color
        candle.configAllSettings(config)
        candle.animate(state.animation)
    }
}