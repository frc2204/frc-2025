package frc.robot.commands.autonomous

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator

class ElevatorL1 : SequentialCommandGroup(
    PositionElevator({ ElevatorConstants.L1_POSITION },
        { it in ElevatorConstants.L1_POSITION - ElevatorConstants.SCORING_OFFSET..ElevatorConstants.L1_POSITION + ElevatorConstants.SCORING_OFFSET }),
)