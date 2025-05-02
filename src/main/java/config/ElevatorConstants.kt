package config

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import com.pathplanner.lib.config.PIDConstants

object ElevatorConstants {
    const val ELEVATOR_MOTOR_ID = 13

    /** must be tuned later */
    val ELEVATOR_PID = PIDConstants(0.8, 0.0, 0.0)  //start testing with d at 10-20% of p
    val ELEVATOR_PID_DEC1 = PIDConstants(0.125, 0.0, 0.0)
    val ELEVATOR_PID_DEC2 = PIDConstants(0.125, 0.0, 0.0) //just raising for auton

    var elevatorMinHeight = 0.0

    /** in inches, need to tune */
    const val ELEVATOR_MAX_HEIGHT = 5.783

    const val ELEVATOR_MAX_ERROR = 1.45

    const val EXTENSION_RATE = 0.25
    const val OFFSET_RATE = 0.15
    const val SCORING_OFFSET = 0.15

    const val ELEVATOR_GEAR_RATIO = 5.0

    val elevatorNeutralMode = NeutralModeValue.Brake
    val elevatorInverted = InvertedValue.CounterClockwise_Positive

    const val ELEVATOR_ENABLE_CURRENT_LIMIT = true
    const val ELEVATOR_CURRENT_LIMIT = 60.0
    const val ELEVATOR_CURRENT_LOWER_LIMIT = 35.0
    const val ELEVATOR_CURRENT_LOWER_TIME = 1.45

    const val DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD = 0.15
    const val VOLTAGE_OPEN_LOOP_RAMP_PERIOD = 0.15
    const val DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD = 0.0
    const val VOLTAGE_CLOSED_LOOP_RAMP_PERIOD = 0.0

    private const val L1_OFFSET = 1.2
    private const val L2_OFFSET = 1.69
    private const val L3_OFFSET = 3.12
    private const val L4_OFFSET = 5.40

    val L1_POSITION = elevatorMinHeight + L1_OFFSET
    val L2_POSITION = elevatorMinHeight + L2_OFFSET
    val L3_POSITION = elevatorMinHeight + L3_OFFSET
    val L4_POSITION = elevatorMinHeight + L4_OFFSET
}