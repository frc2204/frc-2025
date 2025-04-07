package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class SourceIntakeHome : SequentialCommandGroup(
    Commands.runOnce({ EESubsystem.stopEndEffector() }, EESubsystem),
    Commands.runOnce({ IntakeSubsystem.stopIntake() }, IntakeSubsystem),
    PositionElevator({ ElevatorConstants.elevatorMinHeight },
        { it in ElevatorConstants.elevatorMinHeight - ElevatorConstants.OFFSET_RATE..ElevatorConstants.elevatorMinHeight + ElevatorConstants.OFFSET_RATE })
)