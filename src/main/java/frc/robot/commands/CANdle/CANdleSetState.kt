package frc.robot.commands.CANdle

import config.State
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.led.candle
import frc.robot.subsystems.led.config

class CANdleSetState(var state: State,  private val endCondition:Boolean = true) : Command() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        config.stripType = state.color
        candle.configAllSettings(config)
        candle.animate(state.animation)
    }

    override fun execute() {}

    override fun isFinished(): Boolean {
        return endCondition
    }

    override fun end(interrupted: Boolean) {}
}
