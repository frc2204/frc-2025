package frc.robot.subsystems.elevator

import com.ctre.phoenix6.controls.PositionDutyCycle
import com.ctre.phoenix6.hardware.TalonFX
import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object ElevatorSubsystem: SubsystemBase() {
    private val elevatorMotor = TalonFX(ElevatorConstants.ELEVATOR_MOTOR_ID, "rio")

    val position get() = elevatorMotor.position.valueAsDouble

    var desiredPosition = position
        set(position) {
            field = if(position in ElevatorConstants.ELEVATOR_MIN_HEIGHT..ElevatorConstants.ELEVATOR_MAX_HEIGHT)
                position
            else
                this.position
        }

    /**
     * waiting on mechanical fixing gear skipping
     * temporary solution: 2-step decline
     */
//      private val desiredPositionDutyCycle
//          get() =
//          if(((desiredPosition - position) < 0)) {
//              if(((abs(desiredPosition - position)) > ElevatorConstants.ELEVATOR_MAX_ERROR)) {
//                  PositionDutyCycle(desiredPosition).withSlot(1)
//              }
//              else {
//                  PositionDutyCycle(desiredPosition).withSlot(2)
//              }
//          } else {
//              PositionDutyCycle(desiredPosition).withSlot(0)
//          }

    /** temporary fix: slowing down elevator on lowering */
//          private val desiredPositionDutyCycle
//          get() =
//          if(((desiredPosition - position) < 0)) {
//              PositionDutyCycle(desiredPosition).withSlot(1)
//          } else {
//              PositionDutyCycle(desiredPosition).withSlot(0)
//          }

    init{
        elevatorMotor.configurator.apply(CTREConfig.elevatorFXConfig)
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
    }

}