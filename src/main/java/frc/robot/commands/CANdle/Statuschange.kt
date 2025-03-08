package frc.robot.commands.CANdle

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.status.StatusSubsystem

class Statuschange(var endcondition: Boolean = true) : Command() {

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {}

    override fun execute() {
        StatusSubsystem.testCandle()
    }

    override fun isFinished(): Boolean {
        return true
    }

    override fun end(interrupted: Boolean) {}
}
