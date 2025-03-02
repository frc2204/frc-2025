package frc.robot.subsystems.Algae

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.AlgaeConstants
import config.IntakeConstants
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.robot.subsystems.elevator.ElevatorSubsystem.desiredPosition
import frc.robot.subsystems.elevator.ElevatorSubsystem.elevatorMotor
import frc.robot.subsystems.elevator.ElevatorSubsystem.position
import frc.robot.subsystems.intake.AlgaeSPARKMAXConfig.AlgaeMotorConfig
import frc.robot.subsystems.intake.IntakeSparkMAXConfig
import frc.robot.subsystems.intake.IntakeSubsystem.intakeMotor
import org.littletonrobotics.junction.Logger

object AlgaeSubsystem: SubsystemBase() {
    val armMotor = TalonFX(1, "rio")

    var AlgaeIntakePosition: Double = 0.0

    fun moveToPosition(target: Double){
        AlgaeIntakePosition = target

    }

    val leftWristMotor = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
            configure(AlgaeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
        }
    val rightWristMotor = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
            configure(AlgaeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }
    fun StartAlgaeIntake(){
        leftWristMotor.set(AlgaeConstants.ALGAE_INTAKE_SPEED)
        rightWristMotor.set(AlgaeConstants.ALGAE_INTAKE_SPEED)
    }

    fun stopAlgaeIntake(){
        leftWristMotor.stopMotor()
        rightWristMotor.stopMotor()
    }

    fun rotateDownAlgaeIntake() {
    armMotor

    }


    override fun periodic() {
        Logger.recordOutput("ElevatorDesiredPosition", desiredPosition)
        Logger.recordOutput("ElevatorPosition", position)
        Logger.recordOutput("ElevatorVelocity", elevatorMotor.velocity.valueAsDouble)
        Logger.recordOutput("ElevatorCurrent", elevatorMotor.torqueCurrent.valueAsDouble)
        }
}