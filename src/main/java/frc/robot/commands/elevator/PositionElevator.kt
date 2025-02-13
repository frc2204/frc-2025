package frc.robot.commands.elevator

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.elevator.ElevatorSubsystem

class PositionElevator(private val position: () -> Double, private val endCondition: (position: Double) -> Boolean)
    : Command() {

    constructor(position: () -> Double) : this(position, {true})

    init {
        addRequirements(ElevatorSubsystem)
    }
    override fun initialize() {
        ElevatorSubsystem.desiredPosition = position.invoke()
    }

    override fun isFinished(): Boolean {
        return endCondition.invoke(ElevatorSubsystem.position)
    }
}