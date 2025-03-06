package frc.robot.subsystems.end_effector

import com.ctre.phoenix6.configs.TalonFXConfiguration
import config.EEConstants

object EECTREConfig {
    var eeFXConfig = TalonFXConfiguration()
    init {
        eeFXConfig = TalonFXConfiguration()

        /** End effector PID */
        eeFXConfig.Slot0.kP = EEConstants.EE_PID.kP
        eeFXConfig.Slot0.kI = EEConstants.EE_PID.kI
        eeFXConfig.Slot0.kD = EEConstants.EE_PID.kD

        /** MotorOutput States */
        eeFXConfig.MotorOutput.NeutralMode = EEConstants.eeNeutralMode
        eeFXConfig.MotorOutput.Inverted = EEConstants.eeInverted

        /** Gear Ratio */
        eeFXConfig.Feedback.SensorToMechanismRatio = EEConstants.EE_GEAR_RATIO

        /** Current Limiting */
        eeFXConfig.CurrentLimits.SupplyCurrentLimitEnable = EEConstants.EE_ENABLE_CURRENT_LIMIT
        // the absolute maximum amount of supply current allowed after time threshold is exceeded
        eeFXConfig.CurrentLimits.SupplyCurrentLimit = EEConstants.EE_CURRENT_LIMIT
        // The amount of supply current allowed after the regular SupplyCurrentLimit is active for longer than
        // SupplyCurrentLowerTime
        eeFXConfig.CurrentLimits.SupplyCurrentLowerLimit = EEConstants.EE_CURRENT_LOWER_LIMIT
        //Reduces supply current to the SupplyCurrentLowerLimit after limiting to SupplyCurrentLimit for this period
        // of time.
        eeFXConfig.CurrentLimits.SupplyCurrentLowerTime = EEConstants.EE_CURRENT_LOWER_TIME

        /** Open and Closed Loop Ramping */
        // 0% to 100% motor output
        eeFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = EEConstants.DUTY_CYCLE_OPEN_LOOP_RAMP_PERIOD
        // 0V to 12V
        eeFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = EEConstants.VOLTAGE_OPEN_LOOP_RAMP_PERIOD

        eeFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = EEConstants.DUTY_CYCLE_CLOSED_LOOP_RAMP_PERIOD
        eeFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = EEConstants.VOLTAGE_CLOSED_LOOP_RAMP_PERIOD
    }
}