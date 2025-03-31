package frc.robot.subsystems.end_effector

import com.ctre.phoenix6.hardware.TalonFX
import config.EEConstants
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

object EESubsystem: SubsystemBase() {
    private val eeMotor = TalonFX(EEConstants.EE_MOTOR_ID, "rio")
    private val beamBreak = DigitalInput(0)
    val beamBreakState
        get() = beamBreak.get()

    init {
        eeMotor.configurator.apply(EECTREConfig.eeFXConfig)
    }

    fun startEndEffector() {
        eeMotor.set(0.5)
    }

    fun eeScore() {
        eeMotor.set(0.2)
    }

    fun eeScoreAuto() {
        eeMotor.set(0.25)
    }

    fun eeReverse() {
        eeMotor.set(-1.0)
    }

    fun eeStage() {
        eeMotor.set(-0.05)
    }

    fun stopEndEffector() {
        eeMotor.stopMotor()
    }

    override fun periodic() {
        Logger.recordOutput("EndEffectorVelocity", eeMotor.velocity.valueAsDouble)
        Logger.recordOutput("EndEffectorCurrent", eeMotor.torqueCurrent.valueAsDouble)
        Logger.recordOutput("BeamBreakState", beamBreakState)
    }
}