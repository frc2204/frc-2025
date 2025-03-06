package frc.robot.commands.end_effector

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class BeamBreakCommand(private val beamBreakState: () -> Boolean): Command() {
    init {
        addRequirements(EESubsystem, IntakeSubsystem)
    }

    override fun isFinished(): Boolean {
       return beamBreakState.invoke()
    }

    override fun end(interrupted: Boolean) {
        Commands.runOnce({ IntakeSubsystem.stopIntake() }, IntakeSubsystem)
        Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem)
    }
}