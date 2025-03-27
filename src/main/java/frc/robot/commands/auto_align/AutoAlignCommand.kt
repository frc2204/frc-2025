package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.GoalEndState
import com.pathplanner.lib.path.PathPlannerPath
import config.AutoAlignConstants
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj2.command.Command

class AutoAlignCommand(private val desiredPose: Pose2d): Command() {
    override fun initialize() {
        val waypoints = PathPlannerPath.waypointsFromPoses(
            Pose2d(desiredPose.x, desiredPose.y, Rotation2d.fromDegrees(desiredPose.rotation.degrees))
        )

        val testPathReefTwoLeft = PathPlannerPath(
            waypoints,
            AutoAlignConstants.pathConstraints,
            null,
            GoalEndState(0.0, Rotation2d.fromDegrees(120.0))
        )

        AutoBuilder.followPath(testPathReefTwoLeft)
    }

    override fun isFinished(): Boolean {
        return true
    }
}