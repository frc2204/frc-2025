package frc.robot.commands.auto_align

import com.pathplanner.lib.auto.AutoBuilder
import config.CompletePath
import config.LEDConstants
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.led.LEDSubsystem

object AutoAlign {
    fun pathFind(path: CompletePath): Command {
        return SequentialCommandGroup(
            InstantCommand({ LEDSubsystem.state = LEDConstants.IS_AUTO_ALIGNING }, LEDSubsystem),
            AutoBuilder.pathfindThenFollowPath(path.path, path.constraints),
            InstantCommand({ LEDSubsystem.state = LEDConstants.IDLE }, LEDSubsystem)
        )
        }
    }