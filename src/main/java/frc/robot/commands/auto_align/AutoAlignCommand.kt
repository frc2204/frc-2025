package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.AutoAlignConstantsNew
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.DriverStation.Alliance
import edu.wpi.first.wpilibj2.command.Command
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
        val isFlipped =
            DriverStation.getAlliance().isPresent
                    && DriverStation.getAlliance().get() == Alliance.Red
        val fieldWidth:Double = Units.feetToMeters(26.0) + Units.inchesToMeters(5.0);
        val fieldLength:Double = Units.feetToMeters(57.0) + Units.inchesToMeters(6.875);
        var ClosestReefFacePosInList: () -> Int = {
            ReefFaces.indexOf(
                ReefFaces.minByOrNull { reef ->
                    var dx:Double
                    var dy:Double
                    if(isFlipped){
                        dx = (fieldLength - reef.translation.x) - currentPose.translation.x
                        dy = (fieldWidth -reef.translation.y) - currentPose.translation.y
                    } else{
                        dx = reef.translation.x - currentPose.translation.x
                        dy = reef.translation.y - currentPose.translation.y
                    }
                    println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                    println(hypot(dx, dy))
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
