package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.drive.ElevatorSubsystem
import frc.robot.subsystems.drive.ElevatorSubsystem.digit
import frc.robot.util.ElevatorConstants

class MoveElevatorCommand : Command() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(ElevatorSubsystem)
    }

    override fun initialize() {
        ElevatorSubsystem
    }
    override fun periodic() {
        if(ElevatorSubsystem.motor.get() == ElevatorSubsystem.getCurrentPosition()){

        }
    }
    override fun execute() {
        ElevatorSubsystem.moveToNextPosition()
    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false
    }

    override fun end(interrupted: Boolean) {}
}
