package frc.robot.commands.command_groups

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.commands.end_effector.BeamBreakCommand
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class SourceIntake: SequentialCommandGroup(
    PositionElevator({ElevatorConstants.ELEVATOR_MIN_HEIGHT},
        {it in ElevatorConstants.ELEVATOR_MIN_HEIGHT - ElevatorConstants.OFFSET_RATE..ElevatorConstants.ELEVATOR_MIN_HEIGHT + ElevatorConstants.OFFSET_RATE}),
    Commands.runOnce({IntakeSubsystem.intake()}, IntakeSubsystem),
    Commands.runOnce({ EESubsystem.startEndEffector()}, EESubsystem),
    BeamBreakCommand ({ EESubsystem.beamBreakState }, {IntakeSubsystem.intakeCurrent})
)