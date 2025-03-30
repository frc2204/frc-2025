package frc.robot.commands.autonomous

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator

class ZeroElevator: SequentialCommandGroup(
    PositionElevator({ ElevatorConstants.elevatorMinHeight },
        {it in ElevatorConstants.elevatorMinHeight - ElevatorConstants.OFFSET_RATE..ElevatorConstants.elevatorMinHeight + ElevatorConstants.OFFSET_RATE})
)