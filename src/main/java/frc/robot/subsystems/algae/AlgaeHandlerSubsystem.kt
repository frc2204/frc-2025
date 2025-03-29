package frc.robot.subsystems.algae

import com.ctre.phoenix6.controls.PositionDutyCycle
import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.AlgaeHandlerConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object AlgaeHandlerSubsystem: SubsystemBase() {
    private val spinner = SparkMax(AlgaeHandlerConstants.ALGAE_HANDLER_SPINNER_ID, SparkLowLevel.MotorType.kBrushless).apply {
        configure(AlgaeHandlerSparkMAXConfig.algaeSpinnerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }

    private val spinnerCurrent = spinner.outputCurrent

    val isIntakeStalling: Boolean
        get() {
            return spinnerCurrent > AlgaeHandlerConstants.INTAKE_STALLING
        }

    private val wrist = TalonFX(AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_ID, "rio").apply {
        configurator.apply(AlgaeHandlerCTREConfig.algaeWristFXConfig)
    }

    val currentPosition get() = wrist.position.valueAsDouble

    var desiredPosition = currentPosition
        set(position) {
            field = if(position in AlgaeHandlerConstants.wristMinPosition..AlgaeHandlerConstants.WRIST_MAX_POSITION)
                position
            else
                this.currentPosition
        }

    fun deAlgaefy() {
        spinner.set(AlgaeHandlerConstants.DEALGAEFY_OUTPUT)
    }

    fun scoreAlgae() {
        spinner.set(AlgaeHandlerConstants.SCORE_ALGAE_OUTPUT)
    }

    fun stopAlgae() {
        spinner.stopMotor()
    }

    override fun periodic() {
        wrist.setControl(PositionDutyCycle(desiredPosition).withSlot(0))

        Logger.recordOutput("AlgaeWristPosition", currentPosition)
        Logger.recordOutput("AlgaeWristVoltage", wrist.motorVoltage.value)
        Logger.recordOutput("AlgaeSpinnerOutputCurrent", spinnerCurrent)
    }
}