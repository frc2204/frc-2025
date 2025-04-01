package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.intake.IntakeSubsystem

class AutonomousIntake: Command() {
    private var initialTime = 0.0
    init {
        addRequirements(IntakeSubsystem)
        initialTime = Timer.getTimestamp()
    }

    override fun execute() {
        IntakeSubsystem.intake()
    }

    override fun isFinished(): Boolean {
        print("1 & " + IntakeSubsystem.autonIntakeCurrent)
        print("2 & " + ((Timer.getTimestamp() - initialTime) > 20))
        return (IntakeSubsystem.autonIntakeCurrent && ((Timer.getTimestamp() - initialTime) > 20))
    }

    override fun end(interrupted: Boolean) {
        IntakeSubsystem.stopIntake()
    }
}