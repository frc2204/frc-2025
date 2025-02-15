package frc.robot.path_finding_and_follow

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.PathConstraints
import com.pathplanner.lib.path.PathPlannerPath
import edu.wpi.first.math.util.Units

object PathFindingCommand {
    private val intakePath = PathPlannerPath.fromPathFile("Auto_Align_Source.path")
    private val intakePathConstraints = PathConstraints(2.0, 3.0,
        Units.degreesToRadians(360.0), Units.degreesToRadians(540.0))

    val intakePathFindingCommand = AutoBuilder.pathfindThenFollowPath(intakePath, intakePathConstraints)
}