package frc.robot.commands.command_groups


import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.algae.AlgaeSubsystem

class ProcessorHome : SequentialCommandGroup(
    Commands.runOnce({AlgaeSubsystem.stopMotor()}, AlgaeSubsystem)
)
