package config

import com.ctre.phoenix.led.Animation
import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.LarsonAnimation
import com.ctre.phoenix.led.FireAnimation
import com.ctre.phoenix.led.RainbowAnimation
import com.ctre.phoenix.led.RgbFadeAnimation
import com.ctre.phoenix.led.SingleFadeAnimation
import com.ctre.phoenix.led.StrobeAnimation
import com.ctre.phoenix.led.TwinkleAnimation
import com.ctre.phoenix.led.ColorFlowAnimation
object LEDConstants {
    val NUMBER_OF_LEDS = 156
    var IS_DISABLED = State(false, CANdle.LEDStripType.RGB, LarsonAnimation(0,0,0)) //done
//    var IS_TELEOP = State(false, CANdle.LEDStripType.RGB, FireAnimation()) //done
    var IS_AUTO_ALIGNING = State(false, CANdle.LEDStripType.RGB, RainbowAnimation()) //done
    var IS_INTAKING = State(false, CANdle.LEDStripType.RGB, RgbFadeAnimation())//done
    var IS_SCORING = State(false, CANdle.LEDStripType.RGB, SingleFadeAnimation(2,2,2)) //no subsystem
    var IS_ELEVATOR_RAISED = State(false, CANdle.LEDStripType.RGB, ColorFlowAnimation(6,4,7)) //done
    var IDLE = State(false, CANdle.LEDStripType.RGB, StrobeAnimation(6,4,7)) //done
}
class State (var state: Boolean?, val color: CANdle.LEDStripType?, val animation: Animation?)