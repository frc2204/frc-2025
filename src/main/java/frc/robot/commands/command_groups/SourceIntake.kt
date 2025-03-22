package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.commands.end_effector.BeamBreakCommand
import frc.robot.commands.end_effector.StageCoral
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class SourceIntake: SequentialCommandGroup(
    PositionElevator({ElevatorConstants.elevatorMinHeight},
        {it in ElevatorConstants.elevatorMinHeight - ElevatorConstants.OFFSET_RATE..ElevatorConstants.elevatorMinHeight + ElevatorConstants.OFFSET_RATE}),
    Commands.runOnce({IntakeSubsystem.intake()}, IntakeSubsystem),
    Commands.runOnce({ EESubsystem.startEndEffector()}, EESubsystem),
    BeamBreakCommand ({ EESubsystem.beamBreakState }, {IntakeSubsystem.intakeCurrent}),
    StageCoral { EESubsystem.beamBreakState }
)