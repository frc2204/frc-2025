package frc.robot.subsystems.intake

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.IntakeConstants
import config.LEDConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.subsystems.led.LEDSubsystem
import org.littletonrobotics.junction.Logger

object IntakeSubsystem: SubsystemBase() {
    val intakeMotor = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
        configure(IntakeSparkMAXConfig.intakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }

    val intakeCurrent
        get() = intakeMotor.outputCurrent

    fun intake() {
        intakeMotor.set(IntakeConstants.INTAKE_SPEED)
        LEDSubsystem.state = LEDConstants.IS_INTAKING
    }

    fun stopIntake() {
        intakeMotor.stopMotor()
        LEDSubsystem.state = LEDConstants.IDLE
    }

    override fun periodic() {
        Logger.recordOutput("Intake/Output", intakeMotor.appliedOutput)
        Logger.recordOutput("Intake/Velocity", intakeMotor.encoder.velocity)
        Logger.recordOutput("Intake/Current", intakeMotor.outputCurrent)
        Logger.recordOutput("Intake/Temperature", intakeMotor.motorTemperature)
    }
}