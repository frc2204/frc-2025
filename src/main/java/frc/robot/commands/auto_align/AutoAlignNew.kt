package frc.robot.commands.auto_align

import com.pathplanner.lib.path.GoalEndState
import com.pathplanner.lib.path.PathConstraints
import com.pathplanner.lib.path.PathPlannerPath
import config.AutoAlignConstants
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.util.Units

object AutoAlignNew {
    private val pathConstraints = PathConstraints(1.5, 2.0,
        Units.degreesToRadians(360.0), Units.degreesToRadians(540.0))

    val testPathReefTwoLeft = PathPlannerPath(
        AutoAlignConstants.ALIGN_REEF2_LEFT_WAYPOINTS,
        pathConstraints,
        null,
        GoalEndState(0.0, Rotation2d.fromDegrees(120.0))
    )

    init {
        testPathReefTwoLeft.preventFlipping = true
    }
}