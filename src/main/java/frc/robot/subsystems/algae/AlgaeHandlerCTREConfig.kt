package frc.robot.subsystems.algae

import com.ctre.phoenix6.configs.TalonFXConfiguration
import config.AlgaeHandlerConstants

object AlgaeHandlerCTREConfig {
    val algaeWristFXConfig = TalonFXConfiguration()
    init {
        /** End effector PID */
        algaeWristFXConfig.Slot0.kP = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_PID.kP
        algaeWristFXConfig.Slot0.kI = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_PID.kI
        algaeWristFXConfig.Slot0.kD = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_PID.kD

        /** MotorOutput States */
        algaeWristFXConfig.MotorOutput.NeutralMode = AlgaeHandlerConstants.algaeHandlerWristNeutralMode
        algaeWristFXConfig.MotorOutput.Inverted = AlgaeHandlerConstants.algaeHandlerWristInverted

        /** Gear Ratio */
        algaeWristFXConfig.Feedback.SensorToMechanismRatio = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_REDUCTION

        /** Current Limiting */
        algaeWristFXConfig.CurrentLimits.SupplyCurrentLimitEnable = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_ENABLE_CURRENT_LIMIT
        // the absolute maximum amount of supply current allowed after time threshold is exceeded
        algaeWristFXConfig.CurrentLimits.SupplyCurrentLimit = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_CURRENT_LIMIT
        // The amount of supply current allowed after the regular SupplyCurrentLimit is active for longer than
        // SupplyCurrentLowerTime
        algaeWristFXConfig.CurrentLimits.SupplyCurrentLowerLimit = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_CURRENT_LOWER_LIMIT
        //Reduces supply current to the SupplyCurrentLowerLimit after limiting to SupplyCurrentLimit for this period
        // of time.
        algaeWristFXConfig.CurrentLimits.SupplyCurrentLowerTime = AlgaeHandlerConstants.ALGAE_HANDLER_WRIST_CURRENT_LOWER_TIME

        /** Open and Closed Loop Ramping */
        // 0% to 100% motor output
        algaeWristFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = AlgaeHandlerConstants.DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD
        // 0V to 12V
        algaeWristFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = AlgaeHandlerConstants.VOLTAGE_OPEN_LOOP_RAMP_PERIOD

        algaeWristFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = AlgaeHandlerConstants.DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD
        algaeWristFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = AlgaeHandlerConstants.VOLTAGE_CLOSED_LOOP_RAMP_PERIOD
    }
}