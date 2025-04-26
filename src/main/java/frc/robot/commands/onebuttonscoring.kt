package frc.robot.commands

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import frc.robot.commands.auto_align.AutoAlignCommand
import frc.robot.subsystems.drive.Drive
import frc.robot.subsystems.onebuttonscoring.onebuttonscoringSubsystem
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller
enum class Side { LEFT, RIGHT }
class onebuttonscoring(var pose:Drive, val pS5Controller: CommandPS5Controller, val side:Side ) : Command() {
    lateinit var leftpathcommand:Command
    lateinit var rightpathcommand:Command

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(onebuttonscoringSubsystem)
    }

    override fun initialize() {
        leftpathcommand = AutoAlignCommand.findClosestReefFace(AutoAlignCommand.Side.LEFT, pose.pose)
        rightpathcommand = AutoAlignCommand.findClosestReefFace(AutoAlignCommand.Side.RIGHT, pose.pose)
    }

    override fun execute() {
        if (side==Side.LEFT){
            CommandScheduler.getInstance().schedule(leftpathcommand)
        }
        if (side==Side.RIGHT){
            CommandScheduler.getInstance().schedule(rightpathcommand)
        }

    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return !pS5Controller.L1().asBoolean || !pS5Controller.L2().asBoolean
    }

    override fun end(interrupted: Boolean) {
        CommandScheduler.getInstance().cancel(leftpathcommand)
    }
}
