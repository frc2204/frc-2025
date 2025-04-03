package frc.robot.commands.Testing

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.commands.autonomous.AutonomousIntake
import frc.robot.commands.autonomous.StageCoral

class AutonIntakeLoop: SequentialCommandGroup(
    AutonomousIntake(),
    WaitCommand(2.0),
    StageCoral()
)