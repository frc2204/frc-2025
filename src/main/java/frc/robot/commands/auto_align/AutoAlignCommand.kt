package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.GoalEndState
import com.pathplanner.lib.path.PathPlannerPath
import config.AutoAlignConstants
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj2.command.Command

object AutoAlignCommand {
    fun pathFind(targetPose: Pose2d): Command {
        return AutoBuilder.pathfindToPoseFlipped(
            targetPose,
            AutoAlignConstants.pathConstraints,
            0.0
        )
    }
}