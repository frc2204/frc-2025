// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
package frc.robot

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.auto.NamedCommands
import com.pathplanner.lib.commands.PathPlannerAuto
import config.*
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine
import frc.robot.commands.DriveCommands
import frc.robot.commands.auto_align.AutoAlign
import frc.robot.commands.auto_align.AutoAlignNew
import frc.robot.commands.autonomous.Intake
import frc.robot.commands.autonomous.L4Score
import frc.robot.commands.command_groups.*
import frc.robot.commands.end_effector.StageCoral
import frc.robot.subsystems.drive.*
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.vision.*
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser

class RobotContainer {
    // Subsystems
    private var drive: Drive? = null

    // Controllers
    private val xBoxController = CommandXboxController(1)
    private val ps5Controller = CommandPS5Controller(0)

    // Dashboard inputs
    private val autoChooser: LoggedDashboardChooser<Command>

    // Vision
    private var vision: Vision? = null

    /** The container for the robot. Contains subsystems, OI devices, and commands.  */
    init {
        when (Constants.currentMode) {
            Constants.Mode.REAL -> {
                // Real robot, instantiate hardware IO implementations
                drive =
                    Drive(
                        GyroIONavX(),
                        ModuleIOTalonFX(TunerConstants.FrontLeft),
                        ModuleIOTalonFX(TunerConstants.FrontRight),
                        ModuleIOTalonFX(TunerConstants.BackLeft),
                        ModuleIOTalonFX(TunerConstants.BackRight)
                    )

                vision =
                    Vision(
                        { visionRobotPoseMeters: Pose2d?, timestampSeconds: Double, visionMeasurementStdDevs: Matrix<N3?, N1?>? ->
                            drive!!.addVisionMeasurement(
                                visionRobotPoseMeters,
                                timestampSeconds,
                                visionMeasurementStdDevs
                            )
                        },
                        VisionIOLimelight(VisionConstants.camera0Name) { drive!!.rotation },
                        VisionIOLimelight(VisionConstants.camera1Name) { drive!!.rotation })
            }

            Constants.Mode.SIM -> {
                // Sim robot, instantiate physics sim IO implementations
                drive =
                    Drive(
                        object : GyroIO {},
                        ModuleIOSim(TunerConstants.FrontLeft),
                        ModuleIOSim(TunerConstants.FrontRight),
                        ModuleIOSim(TunerConstants.BackLeft),
                        ModuleIOSim(TunerConstants.BackRight)
                    )

                vision =
                    Vision(
                        { visionRobotPoseMeters: Pose2d?, timestampSeconds: Double, visionMeasurementStdDevs: Matrix<N3?, N1?>? ->
                            drive!!.addVisionMeasurement(
                                visionRobotPoseMeters,
                                timestampSeconds,
                                visionMeasurementStdDevs
                            )
                        },
                        VisionIOPhotonVisionSim(
                            VisionConstants.camera0Name,
                            VisionConstants.robotToCamera0
                        ) { drive!!.pose },
                        VisionIOPhotonVisionSim(
                            VisionConstants.camera1Name,
                            VisionConstants.robotToCamera1
                        ) { drive!!.pose })
            }

            else -> {
                // Replayed robot, disable IO implementations
                drive =
                    Drive(
                        object : GyroIO {},
                        object : ModuleIO {},
                        object : ModuleIO {},
                        object : ModuleIO {},
                        object : ModuleIO {})

                vision =
                    Vision({ visionRobotPoseMeters: Pose2d?, timestampSeconds: Double, visionMeasurementStdDevs: Matrix<N3?, N1?>? ->
                        drive!!.addVisionMeasurement(
                            visionRobotPoseMeters,
                            timestampSeconds,
                            visionMeasurementStdDevs
                        )
                    }, object : VisionIO {}, object : VisionIO {})
            }
        }

        NamedCommands.registerCommand("Intake", Intake())
        NamedCommands.registerCommand("L4Score",L4Score())

        // Set up auto routines
        autoChooser = LoggedDashboardChooser("Auto Choices", AutoBuilder.buildAutoChooser())

        // Set up SysId routines
        autoChooser.addOption(
            "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive)
        )
        autoChooser.addOption(
            "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive)
        )
        autoChooser.addOption(
            "Drive SysId (Quasistatic Forward)",
            drive!!.sysIdQuasistatic(SysIdRoutine.Direction.kForward)
        )
        autoChooser.addOption(
            "Drive SysId (Quasistatic Reverse)",
            drive!!.sysIdQuasistatic(SysIdRoutine.Direction.kReverse)
        )
        autoChooser.addOption(
            "Drive SysId (Dynamic Forward)", drive!!.sysIdDynamic(SysIdRoutine.Direction.kForward)
        )
        autoChooser.addOption(
            "Drive SysId (Dynamic Reverse)", drive!!.sysIdDynamic(SysIdRoutine.Direction.kReverse)
        )

        autoChooser.addOption(
            "Testing/Drive Forward 1M", autoDrive1M()
        )
        autoChooser.addOption(
            "Testing/Rotate 90", rotate90()
        )

        autoChooser.addOption(
            "Start1_Reef6_1", start1_reef6_1()
        )

        autoChooser.addOption(
            "Start2_Reef1_1", start2_reef1_1()
        )

        autoChooser.addOption(
            "Start3_Reef2_1", start3_reef2_1()
        )

        autoChooser.addOption(
            "Start1_Reef6&5_2", start1_reef6_5_2()
        )

        autoChooser.addOption(
            "Start2_Reef1&3_2", start2_reef13_2()
        )

        autoChooser.addOption(
            "Start3_Reef2&3_2", start3_reef23_2()
        )

        // Configure the button bindings
        configureButtonBindings()
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a GenericHID or one of its subclasses ([ ] or XboxController), and then passing it to a [ ].
     */
    private fun configureButtonBindings() {
        // Default command, normal field-relative drive
        drive!!.defaultCommand = DriveCommands.joystickDrive(
            drive,
            { -ps5Controller.leftY * 1 },
            { -ps5Controller.leftX * 1 },
            { -ps5Controller.rightX * 1 })

        // Lock to 0° when A button is held
//        controller
//            .a()
//            .whileTrue(
//                DriveCommands.joystickDriveAtAngle(
//                    drive, { -controller.leftY }, { controller.leftX }, { Rotation2d() })
//            )

        // Switch to X pattern when X button is pressed
        //controller.x().onTrue(Commands.runOnce({ drive!!.stopWithX() }, drive))

        // Reset gyro to 0° when B button is pressed
        xBoxController
            .leftStick()
            .onTrue(
                Commands.runOnce(
                    {
                        drive!!.pose = Pose2d(drive!!.pose.translation, Rotation2d())
                    },
                    drive
                )
                    .ignoringDisable(true)
            )

        /** Scoring */
//        // L1
//        controller.x().onTrue(PositionElevator({ElevatorConstants.L1_POSITION},
//            {it in ElevatorConstants.L1_POSITION - ElevatorConstants.OFFSET_RATE..ElevatorConstants.L1_POSITION + ElevatorConstants.OFFSET_RATE}))
//        // L2
//        controller.y().onTrue(PositionElevator({ElevatorConstants.L2_POSITION},
//            {it in ElevatorConstants.L2_POSITION - ElevatorConstants.OFFSET_RATE..ElevatorConstants.L2_POSITION + ElevatorConstants.OFFSET_RATE}))
//        // L3
//        controller.b().onTrue(PositionElevator({ElevatorConstants.L3_POSITION},
//            {it in ElevatorConstants.L3_POSITION - ElevatorConstants.OFFSET_RATE..ElevatorConstants.L3_POSITION + ElevatorConstants.OFFSET_RATE}))
//        // L4
//        controller.a().onTrue(PositionElevator({ElevatorConstants.L4_POSITION},
//            {it in ElevatorConstants.L4_POSITION - ElevatorConstants.OFFSET_RATE..ElevatorConstants.L4_POSITION + ElevatorConstants.OFFSET_RATE}))
//        // trims
//        controller.povUp().onTrue(PositionElevator { ElevatorSubsystem.position + ElevatorConstants.EXTENSION_RATE } )
//        controller.povDown().onTrue(PositionElevator { ElevatorSubsystem.position - ElevatorConstants.EXTENSION_RATE } )

        xBoxController.x().onTrue(ScoreCoral { ElevatorConstants.L1_POSITION })
        xBoxController.x().onFalse(ScoreCoralHome())
        xBoxController.y().onTrue(ScoreCoral { ElevatorConstants.L2_POSITION })
        xBoxController.y().onFalse(ScoreCoralHome())
        xBoxController.b().onTrue(ScoreCoral { ElevatorConstants.L3_POSITION })
        xBoxController.b().onFalse(ScoreCoralHome())
        xBoxController.a().onTrue(ScoreCoral { ElevatorConstants.L4_POSITION })
        xBoxController.a().onFalse(ScoreCoralHome())

        xBoxController.rightTrigger().whileTrue(ReverseIntake())
        xBoxController.rightTrigger().onFalse(ReverseIntakeStop())

        /** Intake commands */
//        controller.leftBumper().onTrue(SourceIntake())
        ps5Controller.L1().onTrue(SourceIntake())
//        controller.leftBumper().onFalse(SourceIntakeHome())
        ps5Controller.L1().onFalse(SourceIntakeHome())

        /** Source auto align */
//        controller.leftTrigger().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_1))
        ps5Controller.L2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_1))
//        controller.rightTrigger().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_2))
        ps5Controller.R2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_2))

        /** Reef auto align */
//        controller.x().and(controller.povLeft().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Left)))
//        ps5Controller.square()
//            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF5_Left))
        ps5Controller.square()
            .and(ps5Controller.povLeft()).whileTrue(AutoBuilder.followPath(AutoAlignNew.testPathReefTwoLeft))
//        controller.x().and(controller.povRight().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Right)))
        ps5Controller.square()
            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF5_Right))

        ps5Controller.circle()
            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF1_Left))

        ps5Controller.circle()
            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF1_Right))

        ps5Controller.cross()
            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF2_Left))

        ps5Controller.cross()
            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF2_Right))

        ps5Controller.triangle()
            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF6_Left))

        ps5Controller.triangle()
            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF6_Right))
    }

    val autonomousCommand: Command
        /**
         * Use this to pass the autonomous command to the main [Robot] class.
         *
         * @return the command to run in autonomous
         */
        get() = autoChooser.get()

    private fun autoDrive1M(): Command {
        return PathPlannerAuto("Forward 1M")
    }

    private fun rotate90(): Command {
        return PathPlannerAuto("Rotate90")
    }

    private fun start1_reef6_1(): Command {
        return PathPlannerAuto("Start1_Reef6_1")
    }

    private fun start2_reef1_1(): Command {
        return PathPlannerAuto("Start2_Reef1_1")
    }

    private fun start3_reef2_1(): Command {
        return PathPlannerAuto("Start3_Reef2_1")
    }

    private fun start1_reef6_5_2(): Command {
        return PathPlannerAuto("Start1_Reef65_2_new")
    }

    private fun start2_reef13_2(): Command {
        return PathPlannerAuto("Start2_Reef13_2")
    }

    private fun start3_reef23_2(): Command {
        return PathPlannerAuto("Start3_Reef23_2")
    }
}
