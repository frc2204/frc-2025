package frc.robot.commands.command_groups


import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.groundintake.GroundIntakeSubsystem

// TODO: Add your sequential commands in the super constructor call,
//       e.g. SequentialCommandGroup(OpenClawCommand(), MoveArmCommand())
class GroundIntakeCommandGroup : SequentialCommandGroup(
    PositionElevator({ElevatorConstants.ELEVATOR_MIN_HEIGHT}, {it <1.0}),
    Commands.runOnce({GroundIntakeSubsystem})
)
