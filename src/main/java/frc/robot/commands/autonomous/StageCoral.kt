package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.end_effector.BeamBreakCommand
import frc.robot.commands.end_effector.StageCoral
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class StageCoral: SequentialCommandGroup(
    Commands.runOnce({ IntakeSubsystem.intake()}, IntakeSubsystem),
    Commands.runOnce({ EESubsystem.startEndEffector()}, EESubsystem),
    BeamBreakCommand ({ EESubsystem.beamBreakState }, { IntakeSubsystem.intakeCurrent }),
    StageCoral { EESubsystem.beamBreakState }
)