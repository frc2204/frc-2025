package frc.robot.subsystems.elevator

import com.ctre.phoenix6.controls.PositionDutyCycle
import com.ctre.phoenix6.hardware.TalonFX
import config.ElevatorConstants
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object ElevatorSubsystem: SubsystemBase() {
    private val elevatorMotor = TalonFX(ElevatorConstants.ELEVATOR_MOTOR_ID, "rio")

    val position get() = elevatorMotor.position.valueAsDouble

    private val throughBorePosition = DutyCycleEncoder(1)

    var desiredPosition = position
        set(position) {
            field = if(position in ElevatorConstants.ELEVATOR_MIN_HEIGHT..ElevatorConstants.ELEVATOR_MAX_HEIGHT)
                position
            else
                this.position
        }

    init{
        elevatorMotor.configurator.apply(CTREConfig.elevatorFXConfig)
        throughBorePosition.setDutyCycleRange(0.0, 1.0)
    }

    override fun periodic() {
        //elevatorMotor.setControl(desiredPositionDutyCycle)
        /** previous */
        elevatorMotor.setControl(PositionDutyCycle(desiredPosition).withSlot(0))

        Logger.recordOutput("ElevatorDesiredPosition", desiredPosition)

        Logger.recordOutput("ElevatorPosition", position)
        Logger.recordOutput("ElevatorVelocity", elevatorMotor.velocity.valueAsDouble)
        Logger.recordOutput("ElevatorCurrent", elevatorMotor.torqueCurrent.valueAsDouble)
        Logger.recordOutput("ElevatorControlMode", elevatorMotor.controlMode.value)

        print("through bore: " + throughBorePosition.get())
        print("through bore converted: " + ((throughBorePosition.get() / 360.0)) * 2048.0)
    }

}