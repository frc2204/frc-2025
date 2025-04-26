package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.AutoAlignConstantsNew
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.drive.Drive
import kotlin.math.hypot

object AutoAlignCommand {
    fun pathFind(targetPose: Pose2d): Command {
        return AutoBuilder.pathfindToPoseFlipped(
            targetPose,
            AutoAlignConstantsNew.pathConstraints,
            0.0
        )
    }

    enum class Side { LEFT, RIGHT }
    fun findClosestReefFace(direction: Side, pose:Pose2d): Command {
        var currentPose = pose
        val ReefFaces = AutoAlignConstantsNew.LIST_OF_REEF_FACE_POSE
        val RightReefSticks = AutoAlignConstantsNew.RIGHT_REEF_STICK_POSE
        val LeftReefSticks = AutoAlignConstantsNew.LEFT_REEF_STICK_POSE
        var ClosestReefFacePosInList: () -> Int = {
            ReefFaces.indexOf(
                ReefFaces.minByOrNull { reef ->
                    var dx = reef.translation.x - currentPose.translation.x
                    var dy = reef.translation.y - currentPose.translation.y
                    hypot(dx, dy)
                } ?: println("uh oh")
            )
            }
        if (direction == Side.LEFT) {
            System.out.println("HELOOOOOOOOO")
            System.out.println(ClosestReefFacePosInList)
            System.out.println("CURRENT POSE: $currentPose")
            println(ClosestReefFacePosInList)
            return pathFind(LeftReefSticks[ClosestReefFacePosInList()])
        } else {
            System.out.println("HELOOOOOOOOO")
            System.out.println(ClosestReefFacePosInList)
            println("HELOOOOOOOOO")
            println(ClosestReefFacePosInList)
            System.out.println("CURRENT POSE: $currentPose")
            return pathFind(RightReefSticks[ClosestReefFacePosInList()])

        }
    }
}
