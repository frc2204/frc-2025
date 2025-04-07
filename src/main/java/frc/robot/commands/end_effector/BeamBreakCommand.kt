package frc.robot.commands.end_effector

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class BeamBreakCommand(private val beamBreakState: () -> Boolean, private val intakeStall: () -> Boolean) : Command() {
    private var isBroken = false

    init {
        addRequirements(EESubsystem, IntakeSubsystem)
    }

    override fun execute() {
        if (!beamBreakState.invoke())
            isBroken = true
    }

    override fun isFinished(): Boolean {
        return (isBroken && beamBreakState.invoke()) || (intakeStall.invoke())
    }

    override fun end(interrupted: Boolean) {
        EESubsystem.stopEndEffector()
        IntakeSubsystem.stopIntake()
        isBroken = false
    }
}