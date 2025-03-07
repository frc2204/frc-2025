package frc.robot.subsystems.intake

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.IntakeConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object IntakeSubsystem: SubsystemBase() {
    private val intakeMotor = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
        configure(IntakeSparkMAXConfig.intakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }

    val intakeCurrent
        get() = intakeMotor.outputCurrent

    fun intake() {
        intakeMotor.set(IntakeConstants.INTAKE_SPEED)
    }

    fun reverseIntake() {
        intakeMotor.set(-IntakeConstants.INTAKE_SPEED)
    }

    fun stopIntake() {
        intakeMotor.stopMotor()
    }

    override fun periodic() {
        Logger.recordOutput("Intake/Output", intakeMotor.appliedOutput)
        Logger.recordOutput("Intake/Velocity", intakeMotor.encoder.velocity)
        Logger.recordOutput("Intake/Current", intakeMotor.outputCurrent)
        Logger.recordOutput("Intake/Temperature", intakeMotor.motorTemperature)
    }
}