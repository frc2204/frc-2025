package frc.robot.subsystems.intake

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.IntakeConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object IntakeSubsystem : SubsystemBase() {
    private val intakeMotor = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
        configure(
            IntakeSparkMAXConfig.intakeMotorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
        )
    }

    private var startTime: Long = 0

    val intakeCurrent //Boolean
        get() = intakeMotor.outputCurrent >= IntakeConstants.INTAKE_STALL_LIMIT

    fun startTimer() {
        startTime = System.currentTimeMillis()
    }

    val autonIntakeCurrent: Boolean
        get() {
            val elapsedTime = System.currentTimeMillis() - startTime
            return elapsedTime >= IntakeConstants.AUTO_INTAKE_DEBOUNCE && intakeMotor.outputCurrent >= IntakeConstants.AUTO_STALL_LIMIT && intakeMotor.outputCurrent <= IntakeConstants.INTAKE_MAX
        }

    fun intake() {
        //intakeMotor.set(IntakeConstants.INTAKE_SPEED)
        intakeMotor.setVoltage(-10.0)
    }

    fun autonIntake() {
        intakeMotor.setVoltage(-10.0)
    }

    fun reverseIntake() {
        intakeMotor.set(-IntakeConstants.INTAKE_SPEED)
    }

    fun stopIntake() {
        intakeMotor.stopMotor()
    }

    fun checkIntakeSpike(): Boolean {
        var spikeStartTime:Double? = null
        var coraled:Boolean = false
        if (intakeMotor.outputCurrent > IntakeConstants.INTAKE_STALL_LIMIT){
            if(spikeStartTime == null){
                spikeStartTime = System.currentTimeMillis().toDouble() //check if the timer startd
            } else if ((System.currentTimeMillis().toDouble() - spikeStartTime!!) > IntakeConstants.SPIKE_DURATION){ // if the current spike is above a value for more than a set time, it is a coral and not false spikes
                coraled = true
            } else {
                coraled = false
                spikeStartTime = null
            }
        }
        return coraled
    }
    override fun periodic() {
        Logger.recordOutput("Intake/Output", intakeMotor.appliedOutput)
        Logger.recordOutput("Intake/Velocity", intakeMotor.encoder.velocity)
        Logger.recordOutput("Intake/Current", intakeMotor.outputCurrent)
        Logger.recordOutput("Intake/Temperature", intakeMotor.motorTemperature)
    }
}