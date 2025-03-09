package frc.robot.commands.statusled

import config.StatusConstants
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.status.StatusSubsystem

class StatusCommand: Command() {

    init {
        addRequirements(StatusSubsystem)
    }

    override fun initialize() {

    }

    override fun execute() {
        StatusSubsystem.setCandleStatus(StatusConstants.testStatus)
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {

    }
//
//    override fun runsWhenDisabled(): Boolean {
//        return true
//    }
}