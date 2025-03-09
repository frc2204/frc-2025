package frc.robot.subsystems.status

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration

object CANdleConfig {
    val config = CANdleConfiguration()

    init {
        config.stripType = CANdle.LEDStripType.RGB
        config.v5Enabled = false
        config.statusLedOffWhenActive = false
        config.vBatOutputMode = CANdle.VBatOutputMode.Modulated
        config.brightnessScalar = 1.0
    }
}