package frc.robot.commands.algae

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.intake.IntakeSubsystem

public class IntakeStallCommand(private val intakeMotor: IntakeSubsystem) : Command() {

    private val stallCurrentThreshold = 30.0 // Amps (adjust as needed)
    private val stallTimeThreshold = 1.0 // Seconds before stopping
    private var stallTimer = Timer()

    override fun initialize() {
        stallTimer.reset()
        stallTimer.start()
    }

    override fun execute() {
        val currentDraw = intakeMotor.getMotorCurrent() // Gets motor current
        println("Current Draw: $currentDraw A") // Debugging output

        if (currentDraw > stallCurrentThreshold) {
            if (stallTimer.hasElapsed(stallTimeThreshold)) {
                println("Intake Stalled! Stopping motor.")
                intakeMotor.set(0.0) // Stop motor
            }
        } else {
            stallTimer.reset() // Reset if current drops below threshold
        }
    }

    override fun isFinished(): Boolean {
        return false // Run until manually stopped
    }

    override fun end(interrupted: Boolean) {
        intakeMotor.set(0.0) // Ensure motor stops when command ends
    }
}
