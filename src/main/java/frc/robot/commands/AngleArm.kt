package frc.robot.commands.algae

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.algae.AlgaeSubsystem

class AngleArm(private val armAngle: () -> Double, private val endCondition: (armAngle: Double) -> Boolean)
    : Command() {

    constructor(armAngle: () -> Double) : this(armAngle, {true})

    init {
        addRequirements(AlgaeSubsystem)
    }
    override fun initialize() {
        AlgaeSubsystem.desiredArmAngle = armAngle.invoke()
    }

    override fun isFinished(): Boolean {
        return endCondition.invoke(AlgaeSubsystem.armAngle)
    }
}
