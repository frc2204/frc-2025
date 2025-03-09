package frc.robot.subsystems.status

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration

object CANdleConfig {
    val config = CANdleConfiguration()

    init {
        config.stripType = CANdle.LEDStripType.RGB
        //config.v5Enabled = true
        config.statusLedOffWhenActive = false
    }
}