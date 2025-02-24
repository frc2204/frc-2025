package frc.robot.commands.path_finding_and_follow

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.PathConstraints
import com.pathplanner.lib.path.PathPlannerPath
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj2.command.Command

object StopPathFindingCommand {
    private val intakePath = PathPlannerPath.fromPathFile("")
    private val intakePathConstraints = PathConstraints(0.0, 0.0,
        Units.degreesToRadians(0.0), Units.degreesToRadians(0.0))

    val stopPathFindingCommand: Command = AutoBuilder.pathfindThenFollowPath(intakePath, intakePathConstraints)
}