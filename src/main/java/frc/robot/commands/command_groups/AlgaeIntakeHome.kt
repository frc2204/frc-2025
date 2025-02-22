package frc.robot.commands.command_groups


import config.AlgaeConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.algae.AngleArm
import frc.robot.subsystems.algae.AlgaeSubsystem

class AlgaeIntakeHome : SequentialCommandGroup(
    Commands.runOnce({AlgaeSubsystem.stopMotor()}, AlgaeSubsystem),
    AngleArm({AlgaeConstants.ARM_MIN_ANGLE}, {it < 1.0})
)
