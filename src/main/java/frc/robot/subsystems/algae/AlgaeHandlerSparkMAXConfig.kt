package frc.robot.subsystems.algae

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import config.AlgaeHandlerConstants

object AlgaeHandlerSparkMAXConfig {
    val algaeSpinnerConfig = SparkMaxConfig()

    init {
        algaeSpinnerConfig.smartCurrentLimit(40)
        algaeSpinnerConfig.idleMode(SparkBaseConfig.IdleMode.kCoast)
        /** verification required */
        algaeSpinnerConfig.inverted(false)
        algaeSpinnerConfig.openLoopRampRate(0.25)
        algaeSpinnerConfig.encoder.positionConversionFactor(AlgaeHandlerConstants.ALGAE_HANDLER_SPINNER_REDUCTION)
    }
}