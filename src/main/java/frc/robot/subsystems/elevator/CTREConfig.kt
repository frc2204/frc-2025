package frc.robot.subsystems.elevator

import com.ctre.phoenix6.configs.TalonFXConfiguration
import config.ElevatorConstants

object CTREConfig {
    var elevatorFXConfig = TalonFXConfiguration()
    init {
        elevatorFXConfig = TalonFXConfiguration()

        /** Elevator PID */
        elevatorFXConfig.Slot0.kP = ElevatorConstants.ELEVATOR_PID.kP
        elevatorFXConfig.Slot0.kI = ElevatorConstants.ELEVATOR_PID.kI
        elevatorFXConfig.Slot0.kD = ElevatorConstants.ELEVATOR_PID.kD

        elevatorFXConfig.Slot1.kP = ElevatorConstants.ELEVATOR_PID_DEC1.kP
        elevatorFXConfig.Slot1.kI = ElevatorConstants.ELEVATOR_PID_DEC1.kI
        elevatorFXConfig.Slot1.kD = ElevatorConstants.ELEVATOR_PID_DEC1.kD

        /** MotorOutput States */
        elevatorFXConfig.MotorOutput.NeutralMode = ElevatorConstants.elevatorNeutralMode
        elevatorFXConfig.MotorOutput.Inverted = ElevatorConstants.elevatorInverted

        /** Gear Ratio */
        elevatorFXConfig.Feedback.SensorToMechanismRatio = ElevatorConstants.ELEVATOR_GEAR_RATIO

        /** Current Limiting */
        elevatorFXConfig.CurrentLimits.SupplyCurrentLimitEnable = ElevatorConstants.ELEVATOR_ENABLE_CURRENT_LIMIT
        // the absolute maximum amount of supply current allowed after time threshold is exceeded
        elevatorFXConfig.CurrentLimits.SupplyCurrentLimit = ElevatorConstants.ELEVATOR_CURRENT_LIMIT
        // The amount of supply current allowed after the regular SupplyCurrentLimit is active for longer than
        // SupplyCurrentLowerTime
        elevatorFXConfig.CurrentLimits.SupplyCurrentLowerLimit = ElevatorConstants.ELEVATOR_CURRENT_LOWER_LIMIT
        //Reduces supply current to the SupplyCurrentLowerLimit after limiting to SupplyCurrentLimit for this period
        // of time.
        elevatorFXConfig.CurrentLimits.SupplyCurrentLowerTime = ElevatorConstants.ELEVATOR_CURRENT_LOWER_TIME

        /** Open and Closed Loop Ramping */
        // 0% to 100% motor output
        elevatorFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = ElevatorConstants.DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD
        // 0V to 12V
        elevatorFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = ElevatorConstants.VOLTAGE_OPEN_LOOP_RAMP_PERIOD

        elevatorFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = ElevatorConstants.DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD
        elevatorFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = ElevatorConstants.VOLTAGE_CLOSED_LOOP_RAMP_PERIOD
    }
}