package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.intake.IntakeSubsystem

class AutonomousIntake : Command() {
    init {
        addRequirements(IntakeSubsystem)
    }

    override fun initialize() {
        IntakeSubsystem.startTimer()
    }

    override fun execute() {
        IntakeSubsystem.autonIntake()
    }

    override fun isFinished(): Boolean {
//        return IntakeSubsystem.autonIntakeCurrent
        return IntakeSubsystem.checkIntakeSpike()
    }

    override fun end(interrupted: Boolean) {
        IntakeSubsystem.stopIntake()
    }
}