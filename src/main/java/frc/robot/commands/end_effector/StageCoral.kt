package frc.robot.commands.end_effector

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.end_effector.EESubsystem

class StageCoral(private val beamBreakState: () -> Boolean): Command() {
    init {
        addRequirements(EESubsystem)
    }

    override fun execute() {
        EESubsystem.eeStage()
    }

    override fun isFinished(): Boolean {
        return !beamBreakState.invoke()
    }

    override fun end(interrupted: Boolean) {
        EESubsystem.stopEndEffector()
    }
}