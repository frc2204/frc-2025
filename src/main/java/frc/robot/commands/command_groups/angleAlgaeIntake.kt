package frc.robot.commands.algaeintake

import config.ShootConstants
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.shoot.ShootSubsystem

class PositionAlgaeIntake(private val position: () -> Double, private val endCondition: (position: Double) -> Boolean)
    : Command() {

    constructor(position: () -> Double) : this(position, {true})

    init {
        addRequirements(ShootSubsystem)
    }
    override fun initialize() {
        ShootSubsystem.desiredAngle = position.invoke()
    }

    override fun isFinished(): Boolean {
        return endCondition.invoke(ShootSubsystem.AIangle)
    }
}