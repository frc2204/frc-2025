package frc.robot.commands.end_effector

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.intake.IntakeSubsystem

class BeamBreakCommand(private val beamBreakState: () -> Boolean): Command() {
    private var isBroken = false
    init {
        addRequirements(EESubsystem, IntakeSubsystem)
    }

    override fun execute() {
        if (!beamBreakState.invoke())
            isBroken = true
    }

    override fun isFinished(): Boolean {
        return (isBroken && beamBreakState.invoke()) || (IntakeSubsystem.intakeCurrent)
    }

    override fun end(interrupted: Boolean) {
        EESubsystem.stopEndEffector()
        IntakeSubsystem.stopIntake()
        isBroken = false
    }
}