package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.AutoAlignConstantsNew
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj2.command.Command

object AutoAlignCommand {
    fun pathFind(targetPose: Pose2d): Command {
        return AutoBuilder.pathfindToPoseFlipped(
            targetPose,
            AutoAlignConstantsNew.pathConstraints,
            0.0
        )
    }
}