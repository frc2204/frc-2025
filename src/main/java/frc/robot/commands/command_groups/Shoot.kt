package frc.robot.commands.command_groups

import config.ElevatorConstants
import config.ShootConstants
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.robot.subsystems.intake.IntakeSubsystem
import frc.robot.subsystems.shoot.ShootSubsystem

class Shoot: SequentialCommandGroup(

    PositionElevator({ ElevatorConstants.ELEVATOR_MAX_HEIGHT}, {it > 5.0}),
    Commands.runOnce({ ShootSubsystem.shoot()}, ShootSubsystem),
    Commands.runOnce( {ElevatorSubsystem.homeElevator(0.0)} , ElevatorSubsystem),
    Commands.runOnce({ShootSubsystem.homeAI(0.0)}, ShootSubsystem)

)






