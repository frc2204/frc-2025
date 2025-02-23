package frc.robot.commands.command_groups

import config.GroundIntakeConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.algaeintake.PositionAlgaeIntake
import frc.robot.subsystems.groundintake.GroundIntakeSubsystem

// TODO: Add your sequential commands in the super constructor call,
//       e.g. SequentialCommandGroup(OpenClawCommand(), MoveArmCommand())
class GroundIntakeUp : SequentialCommandGroup(
    PositionAlgaeIntake({ GroundIntakeConstants.GROUNDINTAKE_MAX_ANGLE}, {it < 0.0}),//change later
    Commands.runOnce({GroundIntakeSubsystem.stopintake()},GroundIntakeSubsystem),
)
