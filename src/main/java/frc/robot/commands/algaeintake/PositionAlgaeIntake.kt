package frc.robot.commands.algaeintake

import config.GroundIntakeConstants
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.groundintake.GroundIntakeSubsystem

class PositionAlgaeIntake(private val position: () -> Double, private val endCondition: (position: Double) -> Boolean)
    : Command() {

    constructor(position: () -> Double) : this(position, {true})

    init {
        addRequirements(GroundIntakeSubsystem)
    }
    override fun initialize() {
        GroundIntakeSubsystem.desiredAngle = position.invoke()
    }

    override fun isFinished(): Boolean {
        return endCondition.invoke(GroundIntakeSubsystem.AIangle)
    }
}