package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem

class ScoreCoralHome: SequentialCommandGroup(
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem),
    PositionElevator({ ElevatorConstants.ELEVATOR_MIN_HEIGHT },
        {it in ElevatorConstants.ELEVATOR_MIN_HEIGHT - ElevatorConstants.OFFSET_RATE..ElevatorConstants.ELEVATOR_MIN_HEIGHT + ElevatorConstants.OFFSET_RATE})
)