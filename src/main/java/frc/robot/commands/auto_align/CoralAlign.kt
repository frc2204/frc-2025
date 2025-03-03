package frc.robot.commands.auto_align

import config.AutoAlignConstants
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.commands.DriveCommands
import frc.robot.subsystems.drive.Drive
import frc.robot.subsystems.vision.Vision
import kotlin.math.abs

class CoralAlign(
    private val driveSubsystem: Drive,
    private val visionSubsystem: Vision,
    private val cameraIndex: Int,
    private val isRight: Boolean
): Command() {

    private var calculatedTheta = 0.0

    init {
        addRequirements(driveSubsystem)
    }

    override fun execute() {
       /** TODO: calculateTheta: offset between desired angle and current angle */
       DriveCommands.joystickDriveAtAngle(driveSubsystem, {0.0}, {0.0}, {Rotation2d(calculatedTheta)})
    }

    override fun isFinished(): Boolean {
       return false
    }

    override fun end(interrupted: Boolean) {
       driveSubsystem.stop()
    }

    private fun calculateOffset(): Double {
        return if (isRight)
            abs(AutoAlignConstants.DESIRED_TX_RIGHT - visionSubsystem.getTargetX(cameraIndex).degrees)
        else
            abs(AutoAlignConstants.DESIRED_TX_LEFT - visionSubsystem.getTargetX(cameraIndex).degrees)
    }
}