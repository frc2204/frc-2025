package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.intake.IntakeSubsystem

class SourceIntakeHome: SequentialCommandGroup(
    Commands.runOnce({IntakeSubsystem.stopIntake()}, IntakeSubsystem),
    PositionElevator({ElevatorConstants.L1_POSITION}, {it > 21.0})
)