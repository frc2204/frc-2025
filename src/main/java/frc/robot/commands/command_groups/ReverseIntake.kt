package frc.robot.commands.command_groups

import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class ReverseIntake: ParallelCommandGroup(
    Commands.runOnce({IntakeSubsystem.reverseIntake()}, IntakeSubsystem),
    Commands.runOnce({EESubsystem.eeReverse()}, EESubsystem)
)