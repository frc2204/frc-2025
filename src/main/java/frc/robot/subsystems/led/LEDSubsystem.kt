package frc.robot.subsystems.led

import com.ctre.phoenix.led.CANdle
import com.ctre.phoenix.led.CANdleConfiguration
import config.State
import edu.wpi.first.wpilibj2.command.SubsystemBase;

object LEDSubsystem : SubsystemBase() {
    var candle = CANdle(0)
    var config: CANdleConfiguration = CANdleConfiguration()

    var state:State = State(null, null, null)
    override fun periodic() {
        CANdleHandler.setState(state)
    }
}
