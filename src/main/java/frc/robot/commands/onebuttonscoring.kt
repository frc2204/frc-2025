package frc.robot.commands

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import frc.robot.commands.auto_align.AutoAlignCommand
import frc.robot.subsystems.drive.Drive
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller
enum class Side { LEFT, RIGHT }
class onebuttonscoring(var pose:Drive, val pS5Controller: CommandPS5Controller, val side:Side ) : Command() {
    lateinit var LeftPathCommand:Command
    lateinit var RightPathCommand:Command

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        LeftPathCommand = AutoAlignCommand.findClosestReefFace(AutoAlignCommand.Side.LEFT, pose.pose)
        RightPathCommand = AutoAlignCommand.findClosestReefFace(AutoAlignCommand.Side.RIGHT, pose.pose)
    }

    override fun execute() {
        if (side==Side.LEFT){
            CommandScheduler.getInstance().schedule(LeftPathCommand)
        }
        if (side==Side.RIGHT){
            CommandScheduler.getInstance().schedule(RightPathCommand)
        }

    }

    override fun isFinished(): Boolean {
        if (side==Side.LEFT){
            return !pS5Controller.L1().asBoolean
        }
        if (side==Side.RIGHT){
            return !pS5Controller.L2().asBoolean
        }
        return false
    }

    override fun end(interrupted: Boolean) {
        CommandScheduler.getInstance().cancel(LeftPathCommand)
        CommandScheduler.getInstance().cancel(RightPathCommand)
    }
}
