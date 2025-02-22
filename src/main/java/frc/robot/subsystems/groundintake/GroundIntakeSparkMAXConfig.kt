package frc.robot.subsystems.intake

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig

object GroundIntakeSparkMAXConfig {
    val groundIntakeMotorConfig = SparkMaxConfig()
    init {
        groundIntakeMotorConfig.smartCurrentLimit(40)
        groundIntakeMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
        groundIntakeMotorConfig.inverted(false)
    }
}
object GroundIntakeSparkMAXConfig2 {
    val groundIntakeMotorConfig = SparkMaxConfig()
    init {
        groundIntakeMotorConfig.smartCurrentLimit(40)
        groundIntakeMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
        groundIntakeMotorConfig.inverted(true)
    }
}

