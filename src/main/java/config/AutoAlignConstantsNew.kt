package config

import com.pathplanner.lib.path.PathConstraints
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.util.Units

object AutoAlignConstantsNew {
    val pathConstraints = PathConstraints(3.85, 2.85, Units.degreesToRadians(300.0), Units.degreesToRadians(480.0))

    val ALIGN_SOURCE_1_POSE = Pose2d(1.2, 7.090, Rotation2d.fromDegrees(-54.0))
    val ALIGN_SOURCE_2_POSE = Pose2d(1.232, 0.723, Rotation2d.fromDegrees(54.0))

    val ALIGN_REEF1_LEFT_POSE = Pose2d(5.757, 3.860, Rotation2d.fromDegrees(180.0))
    val ALIGN_REEF1_MIDDLE = Pose2d(5.757, 4.025, Rotation2d.fromDegrees(180.0))
    val ALIGN_REEF1_RIGHT_POSE = Pose2d(5.757, 4.190, Rotation2d.fromDegrees(180.0))

    val ALIGN_REEF2_LEFT_POSE = Pose2d(4.979, 2.844, Rotation2d.fromDegrees(120.0))
    val ALIGN_REEF2_MIDDLE = Pose2d(5.122,2.927 , Rotation2d.fromDegrees(120.0))
    val ALIGN_REEF2_RIGHT_POSE = Pose2d(5.265, 3.010, Rotation2d.fromDegrees(120.0))

    val ALIGN_REEF3_LEFT_POSE = Pose2d(3.712, 3.015, Rotation2d.fromDegrees(60.0))
    val ALIGN_REEF3_MIDDLE = Pose2d(3.8535,2.9325, Rotation2d.fromDegrees(60.0))
    val ALIGN_REEF3_RIGHT_POSE = Pose2d(3.995, 2.850, Rotation2d.fromDegrees(60.0))

    val ALIGN_REEF4_LEFT_POSE = Pose2d(3.221, 4.194, Rotation2d.fromDegrees(0.0))
    val ALIGN_REEF4_MIDDLE = Pose2d(3.2205,4.027, Rotation2d.fromDegrees(0.0))
    val ALIGN_REEF4_RIGHT_POSE = Pose2d(3.220, 3.860, Rotation2d.fromDegrees(0.0))

    val ALIGN_REEF5_LEFT_POSE = Pose2d(3.996, 5.210, Rotation2d.fromDegrees(-60.0))
    val ALIGN_REEF5_MIDDLE = Pose2d(3.8525,5.1235, Rotation2d.fromDegrees(-60.0))
    val ALIGN_REEF5_RIGHT_POSE = Pose2d(3.711, 5.037, Rotation2d.fromDegrees(-60.0))

    val ALIGN_REEF6_LEFT_POSE = Pose2d(5.267, 5.042, Rotation2d.fromDegrees(-120.0))
    val ALIGN_REEF6_MIDDLE = Pose2d(5.122,5.124, Rotation2d.fromDegrees(-120.0))
    val ALIGN_REEF6_RIGHT_POSE = Pose2d(4.977, 5.206, Rotation2d.fromDegrees(-120.0))

    val RIGHT_REEF_STICK_POSE = listOf(
        ALIGN_REEF6_RIGHT_POSE,
        ALIGN_REEF5_RIGHT_POSE,
        ALIGN_REEF4_RIGHT_POSE,
        ALIGN_REEF3_RIGHT_POSE,
        ALIGN_REEF2_RIGHT_POSE,
        ALIGN_REEF1_RIGHT_POSE
    )

    val LEFT_REEF_STICK_POSE = listOf(
        ALIGN_REEF6_LEFT_POSE,
        ALIGN_REEF5_LEFT_POSE,
        ALIGN_REEF4_LEFT_POSE,
        ALIGN_REEF3_LEFT_POSE,
        ALIGN_REEF2_LEFT_POSE,
        ALIGN_REEF1_LEFT_POSE
    )



    val LIST_OF_REEF_FACE_POSE = listOf(
        ALIGN_REEF1_MIDDLE,
        ALIGN_REEF2_MIDDLE,
        ALIGN_REEF3_MIDDLE,
        ALIGN_REEF4_MIDDLE,
        ALIGN_REEF5_MIDDLE,
        ALIGN_REEF6_MIDDLE)
}
