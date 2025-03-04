package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.CompletePath
import edu.wpi.first.wpilibj2.command.Command

object AutoAlign {
    fun pathFind(path: CompletePath): Command {
        return AutoBuilder.pathfindThenFollowPath(path.path, path.constraints)
    }
}