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

    private var startTime: Long = 0

    val intakeCurrent
        get() = intakeMotor.outputCurrent >= IntakeConstants.INTAKE_STALL_LIMIT

    fun startTimer() {
        startTime = System.currentTimeMillis()
    }

    val autonIntakeCurrent: Boolean
        get() {
            val elapsedTime = System.currentTimeMillis() - startTime
            return elapsedTime >= IntakeConstants.AUTO_INTAKE_DEBOUNCE && intakeMotor.outputCurrent >= IntakeConstants.AUTO_STALL_LIMIT
        }

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