package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.CompletePath
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.vision.VisionConstants

object AutoAlign {
    fun pathFind(path: CompletePath, pathType: PathType): Command {
        if(pathType == PathType.SCORE)
            VisionConstants.cameraStdDevFactors[1] = 0.0
        if(pathType == PathType.SOURCE)
            VisionConstants.cameraStdDevFactors[0] = 0.0

        return AutoBuilder.pathfindThenFollowPath(path.path, path.constraints)
    }

    enum class PathType {
        SCORE,
        SOURCE
    }
}