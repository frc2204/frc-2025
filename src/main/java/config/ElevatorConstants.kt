package config
import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import com.pathplanner.lib.config.PIDConstants

object ElevatorConstants {
    const val ELEVATOR_MOTOR_ID = 13

    /** must be tuned later */
    val ELEVATOR_PID = PIDConstants(0.40, 0.0, 0.0)
    val ELEVATOR_PID_DEC1 = PIDConstants(0.12, 0.0, 0.0)
    val ELEVATOR_PID_DEC2 = PIDConstants(0.20, 0.0, 0.0)

    const val ELEVATOR_MIN_HEIGHT = 0.0
    /** in inches, need to tune */
    const val ELEVATOR_MAX_HEIGHT = 5.75

    const val ELEVATOR_MAX_ERROR = 1.45

    const val EXTENSION_RATE = 0.25
    const val OFFSET_RATE = 0.10

    const val ELEVATOR_GEAR_RATIO = 20.0

    val elevatorNeutralMode = NeutralModeValue.Brake
    val elevatorInverted = InvertedValue.CounterClockwise_Positive

    const val ELEVATOR_ENABLE_CURRENT_LIMIT = true
    const val ELEVATOR_CURRENT_LIMIT = 40.0
    const val ELEVATOR_CURRENT_LOWER_LIMIT = 30.0
    const val ELEVATOR_CURRENT_LOWER_TIME = 1.0

    const val DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val VOLTAGE_OPEN_LOOP_RAMP_PERIOD = 0.35
    const val DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD = 0.0
    const val VOLTAGE_CLOSED_LOOP_RAMP_PERIOD = 0.0

    const val L1_POSITION = 0.855
    const val L2_POSITION = 1.5
    const val L3_POSITION = 3.05
    const val L4_POSITION = 5.3
}