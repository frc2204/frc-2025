package frc.robot.commands.command_groups

import config.ElevatorConstants
import config.StatusConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem
import frc.robot.subsystems.status.StatusSubsystem

class SourceIntakeHome: SequentialCommandGroup(
    Commands.runOnce({ StatusSubsystem.disableStatus()}, StatusSubsystem),
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem),
    Commands.runOnce({IntakeSubsystem.stopIntake()}, IntakeSubsystem),
    PositionElevator({ElevatorConstants.ELEVATOR_MIN_HEIGHT},
        {it in ElevatorConstants.ELEVATOR_MIN_HEIGHT - ElevatorConstants.OFFSET_RATE.. ElevatorConstants.ELEVATOR_MIN_HEIGHT + ElevatorConstants.OFFSET_RATE})
)