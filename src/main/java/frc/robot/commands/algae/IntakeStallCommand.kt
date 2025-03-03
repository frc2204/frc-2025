package frc.robot.commands.algae

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.intake.IntakeSubsystem

public class IntakeStallCommand(private val intakeMotor: IntakeSubsystem) : Command() {

private val stallCurrentThreshold = 30.0 // Amps (adjust as needed)
    private val stallTimeThreshold = 1.0 // Seconds before stopping
    private var stallTimer = Timer()
    private var currentDraw = 0.0 // Store current draw

    override fun initialize() {
        stallTimer.reset()
        stallTimer.start()
    }

    override fun execute() {
        currentDraw = intakeMotor.getMotorCurrent() // Update current draw
        println("Current Draw: $currentDraw A") // Debugging output
        // Reset the timer if current is below the threshold
        if (currentDraw < stallCurrentThreshold) {
            stallTimer.reset()
        }
    }

    override fun isFinished(): Boolean {
        // Check if the current draw has been above the threshold for the stall time
        if (currentDraw > stallCurrentThreshold && stallTimer.hasElapsed(stallTimeThreshold)) {
            println("Intake Stalled! Stopping motor.")
            return true
        }
        return false
    }

    override fun end(interrupted: Boolean) {
        intakeMotor.set(0.0) // Ensure motor stops when command ends
    }
}