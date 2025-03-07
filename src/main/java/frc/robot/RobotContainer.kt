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
import config.AutoAlignConstants
import config.ElevatorConstants
import config.TunerConstants
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine
import frc.robot.commands.DriveCommands
import frc.robot.commands.auto_align.AutoAlign
import frc.robot.commands.command_groups.ScoreCoral
import frc.robot.commands.command_groups.ScoreCoralHome
import frc.robot.commands.command_groups.SourceIntake
import frc.robot.subsystems.drive.*
import frc.robot.subsystems.vision.*
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser

class RobotContainer {
    // Subsystems
    private var drive: Drive? = null

    // Controllers
    private val controller = CommandXboxController(0)
    private val controllerTwo = CommandPS5Controller(1)

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
            { -controllerTwo.leftY * 1 },
            { -controllerTwo.leftX * 1 },
            { -controllerTwo.rightX * 1 })

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
//        controller
//            .leftTrigger()
//            .onTrue(
//                Commands.runOnce(
//                    {
//                        drive!!.pose = Pose2d(drive!!.pose.translation, Rotation2d())
//                    },
//                    drive
//                )
//                    .ignoringDisable(true)
//            )

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

        controller.x().onTrue(ScoreCoral { ElevatorConstants.L1_POSITION })
        controller.x().onFalse(ScoreCoralHome())
        controller.y().onTrue(ScoreCoral { ElevatorConstants.L2_POSITION })
        controller.y().onFalse(ScoreCoralHome())
        controller.b().onTrue(ScoreCoral { ElevatorConstants.L3_POSITION })
        controller.b().onFalse(ScoreCoralHome())
        controller.a().onTrue(ScoreCoral { ElevatorConstants.L4_POSITION })
        controller.a().onFalse(ScoreCoralHome())

        /** Intake commands */
//        controller.leftBumper().onTrue(SourceIntake())
        controllerTwo.L1().onTrue(SourceIntake())
//        controller.leftBumper().onFalse(SourceIntakeHome())
        controllerTwo.L1().onFalse(SourceIntake())

        /** Source auto align */
//        controller.leftTrigger().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_1))
        controllerTwo.L2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_1))
//        controller.rightTrigger().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_2))
        controllerTwo.R2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_2))

        /** Reef auto align */
//        controller.x().and(controller.povLeft().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Left)))
        controllerTwo.square()
            .and(controllerTwo.povLeft().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Left)))
//        controller.x().and(controller.povRight().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Right)))
        controllerTwo.square()
            .and(controllerTwo.povRight().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF4_Right)))
    }

    val autonomousCommand: Command
        /**
         * Use this to pass the autonomous command to the main [Robot] class.
         *
         * @return the command to run in autonomous
         */
        get() = autoChooser.get()
}
