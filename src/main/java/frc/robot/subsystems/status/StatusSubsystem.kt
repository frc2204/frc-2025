package frc.robot.subsystems.status

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration
import config.StatusConstants
import config.StatusState
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object StatusSubsystem: SubsystemBase() {
    val candle = CANdle(StatusConstants.CANDLE_ID, "rio")
    lateinit var statusState: StatusState

    init {
        candle.configAllSettings(CANdleConfiguration())
        candle.configLEDType(CANdle.LEDStripType.GRB)
        candle.configBrightnessScalar(1.0)
    }

    fun disableStatus() {
        candle.animate(null)
        candle.setLEDs(0,0,0)
    }

    fun setCandleStatus(statusState: StatusState) {
        candle.setLEDs(statusState.rgb.r, statusState.rgb.g, statusState.rgb.b)
        //candle.animate(statusState.anim)
    }

    override fun periodic() {
        Logger.recordOutput("CANDLE Error", candle.lastError)
    }
}