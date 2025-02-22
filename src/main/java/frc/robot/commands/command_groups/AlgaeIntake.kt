package frc.robot.commands.command_groups


import config.AlgaeConstants
import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.algae.AngleArm
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.algae.AlgaeSubsystem

class AlgaeIntake : SequentialCommandGroup(
    PositionElevator({ElevatorConstants.ELEVATOR_MIN_HEIGHT}, {it < 1.0}),
    AngleArm({AlgaeSubsystem.desiredArmAngle}, {it > 8.0}),
    Commands.runOnce({AlgaeSubsystem.spinMotor()}, AlgaeSubsystem)
)
