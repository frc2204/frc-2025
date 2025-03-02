package frc.robot.commands.algae

import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.Algae.AlgaeSubsystem
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class DeAlgaefyCommandGroup (private val position: () -> Double, private val endCondition: (position:Double)  ->  Boolean)
    : Command() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(AlgaeSubsystem,IntakeSubsystem,ElevatorSubsystem,)
    }

    override fun initialize() {
        //sets elevator to desired position
        ElevatorSubsystem.desiredPosition = position.invoke()
        AlgaeSubsystem.moveToPosition(0.0)
    }

    override fun execute() {
        //Rotates AlgaeIntake down
        AlgaeSubsystem.armMotor.setPosition(-1.0)
        AlgaeSubsystem.StartAlgaeIntake()
        ElevatorSubsystem.desiredPosition = ElevatorConstants.ELEVATOR_MIN_HEIGHT
        IntakeStallCommand(IntakeSubsystem).schedule()
        AlgaeSubsystem.moveToPosition(1.0)


    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false
    }

    override fun end(interrupted: Boolean) {}
}
