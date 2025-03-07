package frc.robot.commands.CANdle

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.led.CANdleHandler
import frc.robot.subsystems.led.LEDSubsystem.state

class CANdleHandlerCommand : Command() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
    }

    override fun execute() {
        CANdleHandler.setState(state)

//        fun setState(state: State){
//            config.stripType = state.color
//            candle.configAllSettings(config)
//            candle.animate(state.animation)
//        }
    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false
    }

    override fun end(interrupted: Boolean) {}
}
