package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.end_effector.StageCoral
import frc.robot.subsystems.end_effector.EESubsystem

class StageCoral: SequentialCommandGroup(
    StageCoral { EESubsystem.beamBreakState }
)