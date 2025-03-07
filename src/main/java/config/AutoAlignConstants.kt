package config

import com.pathplanner.lib.path.PathConstraints
import com.pathplanner.lib.path.PathPlannerPath
import edu.wpi.first.math.util.Units

object AutoAlignConstants {
    const val DESIRED_TX_LEFT = -15.0
    const val DESIRED_TX_RIGHT = 15.0

    val ALIGN_SOURCE_1 = CompletePath(PathPlannerPath.fromPathFile("Auto_Align_Source_1"),
        PathConstraints(1.0, 1.5,
            Units.degreesToRadians(360.0), Units.degreesToRadians(540.0)))

    val ALIGN_SOURCE_2 = CompletePath(PathPlannerPath.fromPathFile("Auto_Align_Source_2"),
        PathConstraints(1.0, 1.5,
        Units.degreesToRadians(360.0), Units.degreesToRadians(540.0)))

    val ALIGN_REEF4_Right = CompletePath(PathPlannerPath.fromPathFile("Auto_Align_Reef4_Right"),
        PathConstraints(1.5, 1.0,
        Units.degreesToRadians(360.0), Units.degreesToRadians(540.0)))

    val ALIGN_REEF4_Left = CompletePath(PathPlannerPath.fromPathFile("Auto_Align_Reef4_Left"),
        PathConstraints(1.5, 1.0,
            Units.degreesToRadians(360.0), Units.degreesToRadians(540.0)))
}

data class CompletePath(val path: PathPlannerPath, val constraints: PathConstraints)