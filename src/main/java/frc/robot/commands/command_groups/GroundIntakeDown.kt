package frc.robot.commands.command_groups


import config.ElevatorConstants
import config.GroundIntakeConstants
import config.IntakeConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.algaeintake.PositionAlgaeIntake
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.groundintake.GroundIntakeSubsystem

// TODO: Add your sequential commands in the super constructor call,
//       e.g. SequentialCommandGroup(OpenClawCommand(), MoveArmCommand())
class GroundIntakeDown : SequentialCommandGroup(
    PositionElevator({ElevatorConstants.ELEVATOR_MIN_HEIGHT}, {it <1.0}),
    PositionAlgaeIntake({GroundIntakeConstants.GROUNDINTAKE_MIN_ANGLE}, {it < 0.0}),//change later
    Commands.runOnce({GroundIntakeSubsystem.intake()},GroundIntakeSubsystem)
)
