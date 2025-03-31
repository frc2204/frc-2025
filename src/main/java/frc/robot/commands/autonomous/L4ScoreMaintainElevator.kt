package frc.robot.commands.autonomous

import config.EEConstants
import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem

class L4ScoreMaintainElevator: SequentialCommandGroup(
    Commands.runOnce({ EESubsystem.eeScoreAuto() }, EESubsystem),
    WaitCommand(EEConstants.SCORING_WAIT_TIME),
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem)
)