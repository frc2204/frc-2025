package frc.robot.subsystems.end_effector

import com.ctre.phoenix6.hardware.TalonFX
import config.EEConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object EESubsystem: SubsystemBase() {
    private val eeMotor = TalonFX(EEConstants.EE_MOTOR_ID, "rio")

    init {
        eeMotor.configurator.apply(EECTREConfig.eeFXConfig)
    }

    override fun periodic() {
        Logger.recordOutput("EndEffectorVelocity", eeMotor.velocity.valueAsDouble)
        Logger.recordOutput("EndEffectorCurrent", eeMotor.torqueCurrent.valueAsDouble)
    }
}