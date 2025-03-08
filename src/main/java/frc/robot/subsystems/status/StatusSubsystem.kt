package frc.robot.subsystems.status

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration
import com.ctre.phoenix.led.RainbowAnimation
import config.StatusConstants
import config.StatusState
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object StatusSubsystem: SubsystemBase() {
    val candle = CANdle(StatusConstants.CANDLE_ID, "rio")

    init {
        val config = CANdleConfiguration()
        config.stripType = CANdle.LEDStripType.RGB
        candle.configAllSettings(config)
    }

    fun disableStatus() {
        candle.animate(null)
        candle.setLEDs(0,0,0)
        println("CandleStatus Disabled")
    }

    fun setCandleStatus(statusState: StatusState) {
        candle.setLEDs(statusState.rgb.r, statusState.rgb.g, statusState.rgb.b)
        candle.animate(statusState.anim)
        println("CandleStatus Set")
    }

    fun testCandle() {
        candle.setLEDs(255,0,0)
        candle.animate(RainbowAnimation())
        println("testingcAndle Set")
    }

    override fun periodic() {
        Logger.recordOutput("CANDLE Error", candle.lastError)
    }
}