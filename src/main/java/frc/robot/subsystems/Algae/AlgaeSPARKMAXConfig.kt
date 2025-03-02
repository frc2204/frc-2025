package frc.robot.subsystems.intake

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig

object AlgaeSPARKMAXConfig {
 val AlgaeMotorConfig = SparkMaxConfig()
 init {
  AlgaeMotorConfig.smartCurrentLimit(40)
  AlgaeMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
  AlgaeMotorConfig.inverted(true)
 }
}