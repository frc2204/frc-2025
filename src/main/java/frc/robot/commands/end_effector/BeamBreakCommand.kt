package frc.robot.commands.end_effector

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.commands.elevator.PositionElevator
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class BeamBreakCommand(private val bbState: () -> Boolean): Command() {
    init {
        addRequirements(EESubsystem)
    }

    override fun isFinished(): Boolean {
        return bbState.invoke()
    }

    override fun end(interrupted: Boolean) {
        Commands.runOnce({ IntakeSubsystem.stopIntake()}, IntakeSubsystem)
        Commands.runOnce({ EESubsystem.endEffectorStop()}, EESubsystem)
        PositionElevator({ ElevatorConstants.L1_POSITION}, {it > 21.0})
    }
}