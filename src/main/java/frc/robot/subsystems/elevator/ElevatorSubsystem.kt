package frc.robot.subsystems.elevator

import com.ctre.phoenix6.controls.PositionDutyCycle
import com.ctre.phoenix6.hardware.TalonFX
import config.ElevatorConstants
import config.ElevatorConstants.L3_POSITION
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object ElevatorSubsystem : SubsystemBase() {
    private val elevatorMotor = TalonFX(ElevatorConstants.ELEVATOR_MOTOR_ID, "rio")

    var isAutonomousActive: Boolean = false

    val position get() = elevatorMotor.position.valueAsDouble

    val isElevatorRaised: Boolean
        get() = position >= L3_POSITION

    var desiredPosition = position
        set(position) {
            field = if (position in ElevatorConstants.elevatorMinHeight..ElevatorConstants.ELEVATOR_MAX_HEIGHT)
                position
            else
                this.position
        }

    init {
        elevatorMotor.configurator.apply(CTREConfig.elevatorFXConfig)
    }

    override fun periodic() {
        //elevatorMotor.setControl(desiredPositionDutyCycle)
        if ((desiredPosition < position) && !isAutonomousActive) { // if elevator going down in teleop use slot 1
            elevatorMotor.setControl(PositionDutyCycle(desiredPosition).withSlot(1))
        } else if ((desiredPosition < position) && isAutonomousActive){
            elevatorMotor.setControl(PositionDutyCycle(desiredPosition).withSlot(2)) // if elevator going down in auton use slot 2
        } else {
            elevatorMotor.setControl(PositionDutyCycle(desiredPosition).withSlot(0)) //if elevator going up use slot 0
        }

//        if (isAutonomousActive) {
//            elevatorMotor.setControl(PositionDutyCycle(desiredPosition).withSlot(0))
//        }

        Logger.recordOutput("ElevatorDesiredPosition", desiredPosition)

        Logger.recordOutput("ElevatorPosition", position)
        Logger.recordOutput("ElevatorVelocity", elevatorMotor.velocity.valueAsDouble)
        Logger.recordOutput("ElevatorCurrent", elevatorMotor.torqueCurrent.valueAsDouble)
        Logger.recordOutput("ElevatorControlMode", elevatorMotor.controlMode.value)
    }

}