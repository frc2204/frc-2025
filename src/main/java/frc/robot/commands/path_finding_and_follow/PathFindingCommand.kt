package frc.robot.commands.path_finding_and_follow

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.PathConstraints
import com.pathplanner.lib.path.PathPlannerPath
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj2.command.Command

object PathFindingCommand {
    private val intakePath = PathPlannerPath.fromPathFile("Auto_Align_Source")
    private val intakePathConstraints = PathConstraints(1.0, 1.5,
        Units.degreesToRadians(360.0), Units.degreesToRadians(540.0))

    val intakePathFindingCommand: Command = AutoBuilder.pathfindThenFollowPath(intakePath, intakePathConstraints)
}