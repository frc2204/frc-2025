package frc.robot.commands.autonomous

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator

class ElevatorRaise : SequentialCommandGroup(
    PositionElevator({ ElevatorConstants.L4_POSITION },
        { it in ElevatorConstants.L4_POSITION - ElevatorConstants.SCORING_OFFSET..ElevatorConstants.L4_POSITION + ElevatorConstants.SCORING_OFFSET }),
)