package frc.robot.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration
import config.State

//data class State (var state: Boolean, val color: CANdle.LEDStripType, val animation: Animation)
var candle = CANdle(0)
var config = CANdleConfiguration()
var lastState:State = State(null,null,null)
object CANdleHandler {
    fun setState(state: State){
        if(state!= lastState){
            config.stripType = state.color
            candle.configAllSettings(config)
            candle.animate(state.animation)
            lastState = state
        }
    }
}