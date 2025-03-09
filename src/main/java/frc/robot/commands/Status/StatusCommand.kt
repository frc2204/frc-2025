package frc.robot.commands.Status

import config.StatusState
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.status.StatusSubsystem

class StatusCommand(private val state: () -> StatusState): Command() {
    init {
        addRequirements(StatusSubsystem)
    }

    override fun initialize() {
        StatusSubsystem.candle.setLEDs(state.invoke().rgb.r, state.invoke().rgb.g, state.invoke().rgb.b)
    }

    override fun execute() {
        //StatusSubsystem.candle.animate()
    }
}