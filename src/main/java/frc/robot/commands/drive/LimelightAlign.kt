package frc.robot.commands.drive

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.drive.Drive
import frc.robot.subsystems.vision.Vision
import kotlin.math.abs

class LimelightAlign(
    private val drive: Drive,
    private val vision: Vision,
    private val cameraIndex: Int = 0
) : Command() {

    // PID controller for rotation
    private val rotationController = PIDController(
        0.015,  // P: Start small and tune up
        0.0,    // I: Usually not needed for alignment
        0.001   // D: Helps dampen oscillation
    ).apply {
        setTolerance(1.0) // Degrees of acceptable error
    }

    init {
        addRequirements(drive)
    }

    override fun execute() {
        // Get tx value (horizontal offset from crosshair in degrees)
        val tx = vision.getTargetX(cameraIndex).degrees
        
        // Calculate rotation speed using PID
        // Target is 0 degrees (centered in view)
        val rotationSpeed = -rotationController.calculate(tx, 0.0)
            .coerceIn(-0.5, 0.5) // Limit maximum rotation speed
        
        // Apply rotation while keeping X/Y movement at 0
        drive.runVelocity(
            ChassisSpeeds(
                0.0,          // X velocity
                0.0,          // Y velocity
                rotationSpeed // Rotation velocity
            )
        )
    }

    override fun isFinished(): Boolean {
        return rotationController.atSetpoint()
    }

    override fun end(interrupted: Boolean) {
        // Stop all movement
        drive.runVelocity(ChassisSpeeds())
    }
} 