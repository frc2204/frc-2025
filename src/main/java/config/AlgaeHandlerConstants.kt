package config

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import com.pathplanner.lib.config.PIDConstants

object AlgaeHandlerConstants {
    const val ALGAE_HANDLER_WRIST_ID = 15
    const val ALGAE_HANDLER_SPINNER_ID = 16

    /** Wrist CTRE configs */

    val ALGAE_HANDLER_WRIST_PID = PIDConstants(2.0, 0.0, 0.0)

    const val ALGAE_HANDLER_WRIST_REDUCTION = 25.0

    val algaeHandlerWristNeutralMode = NeutralModeValue.Brake
    val algaeHandlerWristInverted = InvertedValue.Clockwise_Positive

    const val ALGAE_HANDLER_WRIST_ENABLE_CURRENT_LIMIT = true
    const val ALGAE_HANDLER_WRIST_CURRENT_LIMIT = 40.0
    const val ALGAE_HANDLER_WRIST_CURRENT_LOWER_LIMIT = 30.0
    const val ALGAE_HANDLER_WRIST_CURRENT_LOWER_TIME = 1.0

    const val DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val VOLTAGE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD = 0.0
    const val VOLTAGE_CLOSED_LOOP_RAMP_PERIOD = 0.0

    /** Wrist Constants */

    var wristMinPosition = 0.0
    private const val MAX_OFFSET = 1.0
    private const val L1_ALGAE_OFFSET = 0.0
    private const val L2_ALGAE_OFFSET = 0.0

    val WRIST_MAX_POSITION = wristMinPosition + MAX_OFFSET
    val L1_ALGAE_POSITION = wristMinPosition + L1_ALGAE_OFFSET
    val L2_ALGAE_POSITION = wristMinPosition + L2_ALGAE_OFFSET

    /** Spinner SparkMAX configs */

    const val ALGAE_HANDLER_SPINNER_REDUCTION = 12.5

    /** Spinner Constants */

    const val DEALGAEFY_OUTPUT = 0.2
    const val SCORE_ALGAE_OUTPUT = -0.7
    const val INTAKE_STALLING = 30.0
}