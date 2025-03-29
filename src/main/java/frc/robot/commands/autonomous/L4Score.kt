package frc.robot.commands.autonomous

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem

class L4Score: SequentialCommandGroup(
    PositionElevator({ ElevatorConstants.L4_POSITION },
        {it in ElevatorConstants.L4_POSITION - ElevatorConstants.SCORING_OFFSET .. ElevatorConstants.L4_POSITION + ElevatorConstants.SCORING_OFFSET}),
    Commands.runOnce({ EESubsystem.eeScoreAuto() }, EESubsystem),
    WaitCommand(0.7),
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem),
    PositionElevator({ ElevatorConstants.elevatorMinHeight },
        {it in ElevatorConstants.elevatorMinHeight - ElevatorConstants.OFFSET_RATE..ElevatorConstants.elevatorMinHeight + ElevatorConstants.OFFSET_RATE})
)