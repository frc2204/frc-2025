package frc.robot.subsystems.intake

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig

object IntakeSparkMAXConfig {
    val intakeMotorConfig = SparkMaxConfig()
    init {
        intakeMotorConfig.smartCurrentLimit(40)
        intakeMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast)
        intakeMotorConfig.inverted(true)
    }
}