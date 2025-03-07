package config

import com.ctre.phoenix.led.Animation
import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.LarsonAnimation

object LEDConstants {
    val NUMBER_OF_LEDS = 160
    var IS_DISABLED = State(false, CANdle.LEDStripType.RGB, LarsonAnimation(0,0,0))
    var IS_TELEOP = State(false, CANdle.LEDStripType.RGB, LarsonAnimation(0,0,0))
    var IS_AUTO_ALIGNING = false
    var IS_INTAKING = false
    var IS_SCORING = false
    var IS_ELEVATOR_RAISED = false

}
public class State (var state: Boolean?, val color: CANdle.LEDStripType?, val animation: Animation?)