package frc.robot.subsystems.elevator

import com.ctre.phoenix6.controls.DutyCycleOut
import com.ctre.phoenix6.hardware.TalonFX
import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object ElevatorSubsystem: SubsystemBase() {
    private val elevatorMotor = TalonFX(ElevatorConstants.ELEVATOR_MOTOR_ID, "rio")
    val position get() = elevatorMotor.position.valueAsDouble
    /** must be properly declared */
    var desiredPosition = 0.0
    var extensionOffset = 0.0

    init{
        elevatorMotor.configurator.apply(CTREConfig.elevatorFXConfig)
    }

    override fun periodic() {
        elevatorMotor.setPosition(desiredPosition)

        Logger.recordOutput("ElevatorPosition", position)
        Logger.recordOutput("ElevatorVelocity", elevatorMotor.velocity.valueAsDouble)
        Logger.recordOutput("ElevatorCurrent", elevatorMotor.torqueCurrent.valueAsDouble)
    }

}