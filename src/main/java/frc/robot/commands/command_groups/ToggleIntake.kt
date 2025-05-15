package frc.robot.commands.command_groups

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.commands.end_effector.StageCoral
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class ToggleIntake: Command() {
    override fun initialize() {
       addRequirements(EESubsystem, IntakeSubsystem)
    }

    override fun execute() {
        SourceIntake()
    }

    override fun end(interrupted: Boolean) {
        StageCoral { EESubsystem.beamBreakState }
        SourceIntakeHome()
    }

    override fun isFinished(): Boolean {
        return false
    }
}