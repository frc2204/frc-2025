package frc.robot.commands.command_groups

import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class ReverseIntakeStop: ParallelCommandGroup(
    Commands.runOnce({ IntakeSubsystem.stopIntake() }, IntakeSubsystem),
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem)
)