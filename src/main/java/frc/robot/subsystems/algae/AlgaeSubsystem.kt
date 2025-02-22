package frc.robot.subsystems.algae

import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.AlgaeConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object AlgaeSubsystem : SubsystemBase() {
 private val armMotor = TalonFX(AlgaeConstants.ARM_MOTOR_ID)
 private val leftIntakeMotor = SparkMax(AlgaeConstants.LEFT_INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless)
 private val rightIntakeMotor = SparkMax(AlgaeConstants.RIGHT_INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless)

 val leftIntakeMotorCurrent
  get() = leftIntakeMotor.outputCurrent

 val rightIntakeMotorCurrent
  get() = rightIntakeMotor.outputCurrent

 val armAngle get() = armMotor.position.valueAsDouble

 var desiredArmAngle = 0.0
  set(armAngle) {
   field = if(armAngle in AlgaeConstants.ARM_MIN_ANGLE..AlgaeConstants.ARM_MAX_ANGLE)
    armAngle
   else
    this.armAngle
  }

 init{
  armMotor.configurator.apply(TalonFXConfig.AlgaeFXConfig)
 }

 fun spinMotor(){
  leftIntakeMotor.set(AlgaeConstants.LEFT_INTAKE_SPEED)
  rightIntakeMotor.set(AlgaeConstants.RIGHT_INTAKE_SPEED)
 }
 fun stopMotor(){
  leftIntakeMotor.stopMotor()
  rightIntakeMotor.stopMotor()
 }

 override fun periodic() {
  armMotor.setPosition(desiredArmAngle)

  Logger.recordOutput("DesiredArmAngle", desiredArmAngle)
  Logger.recordOutput("CurrentArmAngle", armAngle)
  Logger.recordOutput("ArmVelocity", armMotor.velocity.valueAsDouble)
  Logger.recordOutput("ArmCurrent", armMotor.torqueCurrent.valueAsDouble)

  Logger.recordOutput("LeftAlgaeIntake/Output", leftIntakeMotor.appliedOutput)
  Logger.recordOutput("LeftAlgaeIntake/Velocity", leftIntakeMotor.encoder.velocity)
  Logger.recordOutput("LeftAlgaeIntake/Current", leftIntakeMotor.outputCurrent)
  Logger.recordOutput("LeftAlgaeIntake/Temperature", leftIntakeMotor.motorTemperature)

  Logger.recordOutput("RightAlgaeIntake/Output", rightIntakeMotor.appliedOutput)
  Logger.recordOutput("RightAlgaeIntake/Velocity", rightIntakeMotor.encoder.velocity)
  Logger.recordOutput("RightAlgaeIntake/Current", rightIntakeMotor.outputCurrent)
  Logger.recordOutput("RightAlgaeIntake/Temperature", rightIntakeMotor.motorTemperature)
 }

}