package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem

class ScoreCoral(private val desiredPosition: () -> Double): SequentialCommandGroup(
    PositionElevator({ desiredPosition.invoke() },
        {it in desiredPosition.invoke() - ElevatorConstants.SCORING_OFFSET ..desiredPosition.invoke() + ElevatorConstants.SCORING_OFFSET}),
    Commands.runOnce({ EESubsystem.eeScore() }, EESubsystem),

)