package config

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import com.pathplanner.lib.config.PIDConstants

object EEConstants {
    const val EE_MOTOR_ID = 14

    val EE_PID = PIDConstants(0.2, 0.0, 0.0)

    const val EE_GEAR_RATIO = 5.0

    val eeNeutralMode = NeutralModeValue.Brake
    val eeInverted = InvertedValue.CounterClockwise_Positive

    const val EE_ENABLE_CURRENT_LIMIT = true
    const val EE_CURRENT_LIMIT = 40.0
    const val EE_CURRENT_LOWER_LIMIT = 30.0
    const val EE_CURRENT_LOWER_TIME = 1.0

    const val DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val VOLTAGE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD = 0.0
    const val VOLTAGE_CLOSED_LOOP_RAMP_PERIOD = 0.0

    const val SCORING_WAIT_TIME = 0.3
}