package frc.robot.subsystems.algae

import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import config.AlgaeHandlerConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase

object AlgaeHandlerSubsystem: SubsystemBase() {
    private val spinner = SparkMax(AlgaeHandlerConstants.ALGAE_HANDLER_SPINNER_ID, SparkLowLevel.MotorType.kBrushless).apply {
        configure(AlgaeHandlerSparkMAXConfig.algaeSpinnerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }

    private val wrist = TalonFX(AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_ID, "rio").apply {
        configurator.apply(AlgaeHandlerCTREConfig.algaeWristFXConfig)
    }
}