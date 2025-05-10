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
import config.AutoAlignConstantsNew
import config.ElevatorConstants
import config.TunerConstants
import kotlin.math.hypot
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.Trigger
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine
import frc.robot.commands.DriveCommands
import frc.robot.commands.Side
import frc.robot.commands.auto_align.AutoAlignCommand
import frc.robot.commands.autonomous.*
import frc.robot.commands.command_groups.*
import frc.robot.commands.elevator.PositionElevator
import frc.robot.commands.onebuttonscoring
import frc.robot.subsystems.drive.*
import frc.robot.subsystems.end_effector.EESubsystem
import frc.robot.subsystems.vision.*
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser
//import frc.utils.Utils.ElasticUtil;


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
        NamedCommands.registerCommand("L4Score", L4Score())
        NamedCommands.registerCommand("L3Score", L3Score())
        NamedCommands.registerCommand("ElevatorL1", ElevatorL1())
        NamedCommands.registerCommand("ElevatorRaise", ElevatorRaise())
        NamedCommands.registerCommand("StageCoral", StageCoral())
        NamedCommands.registerCommand("L3ScoreMaintainElevator", L3ScoreMaintainElevator())
        NamedCommands.registerCommand("L4ScoreMaintainElevator", L4ScoreMaintainElevator())
        NamedCommands.registerCommand("ZeroElevator", ZeroElevator())
        NamedCommands.registerCommand("AutonIntake", AutonomousIntake())

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
        //stunned making swerve sensitivity lower so it wont bounce when hitting source
//        ps5Controller.R2().onTrue(DriveCommands.stunned())
//        ps5Controller.R2().onFalse(DriveCommands.unstunned())

        var isStunned: Boolean = false
        xBoxController.leftBumper().onTrue(InstantCommand({ isStunned = !isStunned }))


        drive!!.defaultCommand = DriveCommands.joystickDrive(
            drive,
            { -ps5Controller.leftY * 1 },
            { -ps5Controller.leftX * 1 },
            { -ps5Controller.rightX * 1 },
            { isStunned }
            )




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

        //old scoring mapping
//        xBoxController.x().onTrue(ScoreCoral { ElevatorConstants.L1_POSITION })
//        xBoxController.x().onFalse(ScoreCoralHome())
//        xBoxController.y().onTrue(ScoreCoral { ElevatorConstants.L2_POSITION })
//        xBoxController.y().onFalse(ScoreCoralHome())
//        xBoxController.b().onTrue(ScoreCoral { ElevatorConstants.L3_POSITION })
//        xBoxController.b().onFalse(ScoreCoralHome())
//        xBoxController.a().onTrue(ScoreCoral { ElevatorConstants.L4_POSITION })
//        xBoxController.a().onFalse(ScoreCoralHome())


//        xBoxController.x().whileTrue(Commands.runOnce({DriveCommands.stun()}))

        xBoxController.x().onTrue(PositionElevator { ElevatorConstants.L1_POSITION })
        xBoxController.x().onFalse(ScoreCoralHome())
        xBoxController.y().onTrue(PositionElevator { ElevatorConstants.L2_POSITION })
        xBoxController.y().onFalse(ScoreCoralHome())
        xBoxController.b().onTrue(PositionElevator { ElevatorConstants.L3_POSITION })
        xBoxController.b().onFalse(ScoreCoralHome())
        xBoxController.a().onTrue(PositionElevator { ElevatorConstants.L4_POSITION })
        xBoxController.a().onFalse(ScoreCoralHome())

        xBoxController.leftTrigger().whileTrue(Commands.runOnce({ EESubsystem.eeScore() }, EESubsystem))
        xBoxController.rightBumper().whileTrue(SourceIntake())
        xBoxController.rightBumper().onFalse(SourceIntakeHome())
        xBoxController.rightTrigger().whileTrue(ReverseIntake())
        xBoxController.rightTrigger().onFalse(ReverseIntakeStop())

//        xBoxController.leftBumper().onTrue( Commands.runOnce{StunnedSubsystem.ChangeStunned()})

        /** Intake commands */
//        ps5Controller.L1().onTrue(SourceIntake())
//        ps5Controller.L1().onFalse(SourceIntakeHome())

//        ps5Controller.R1().onTrue(SourceIntake())
        var isToggledOn: Boolean = false
        ps5Controller.R1().onTrue(InstantCommand({ isToggledOn = !isToggledOn }))
        Trigger { isToggledOn }
            .whileTrue(SourceIntake())
        Trigger { !isToggledOn }
            .onTrue(SourceIntakeHome())

        /** Source auto align */
//        ps5Controller.L2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_1))
//        ps5Controller.R2().whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_SOURCE_2))
//        ps5Controller.L2().whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_SOURCE_1_POSE))
//        ps5Controller.R2().whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_SOURCE_2_POSE))


        /** Reef auto align */
//        ps5Controller.square()
//            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF5_Left))
//        ps5Controller.square()
//            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF5_Right))
//
//        ps5Controller.circle()
//            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF1_Left))
//
//        ps5Controller.circle()
//            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF1_Right))
//
//        ps5Controller.cross()
//            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF2_Left))
//
//        ps5Controller.cross()
//            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF2_Right))
//
//        ps5Controller.triangle()
//            .and(ps5Controller.povLeft()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF6_Left))
//
//        ps5Controller.triangle()
//            .and(ps5Controller.povRight()).whileTrue(AutoAlign.pathFind(AutoAlignConstants.ALIGN_REEF6_Right))


        //old reef autoalign for aiden
//        ps5Controller.square()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF5_LEFT_POSE))
//        ps5Controller.square()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF5_RIGHT_POSE))
//
//        ps5Controller.circle()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF1_LEFT_POSE))
//        ps5Controller.circle()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF1_RIGHT_POSE))
//
//        ps5Controller.cross()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF2_LEFT_POSE))
//        ps5Controller.cross()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF2_RIGHT_POSE))
//
//        ps5Controller.triangle()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF6_LEFT_POSE))
//        ps5Controller.triangle()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF6_RIGHT_POSE))
//
//        ps5Controller.touchpad()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF4_LEFT_POSE))
//        ps5Controller.touchpad()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF4_RIGHT_POSE))
//
//        ps5Controller.options()
//            .and(ps5Controller.povLeft())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF3_LEFT_POSE))
//        ps5Controller.options()
//            .and(ps5Controller.povRight())
//            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF3_RIGHT_POSE))


        ps5Controller.R2().whileTrue(Commands.runOnce({ DriveCommands.stun() }))
        ps5Controller.R2().whileFalse(Commands.runOnce({ DriveCommands.unstun() }))

        ps5Controller.L1().whileTrue(onebuttonscoring(drive!!,ps5Controller,Side.LEFT))
        ps5Controller.L2().whileTrue(onebuttonscoring(drive!!,ps5Controller,Side.RIGHT))
// source autoalign
        ps5Controller.L2().and(ps5Controller.R2().whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_SOURCE_1_POSE)))
        ps5Controller.L2().and(ps5Controller.R2().whileTrue(SourceIntake()))
        ps5Controller.L2().and(ps5Controller.R2().onFalse(SourceIntakeHome()))
    //new reef autoalign for daniel
        ps5Controller.L1()
            .and(
                ps5Controller.triangle()
                    .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF1_LEFT_POSE))
            )
        ps5Controller.L2()
            .and(
                ps5Controller.triangle()
                    .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF1_RIGHT_POSE))
            )
        ps5Controller.L1()
            .and(
                ps5Controller.triangle()
                    .and(
                        ps5Controller.circle()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF2_LEFT_POSE))
                    )
            )
        ps5Controller.L2()
            .and(
                ps5Controller.triangle()
                    .and(
                        ps5Controller.circle()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF2_RIGHT_POSE))
                    )
            )
        ps5Controller.L1()
            .and(
                ps5Controller.circle()
                    .and(
                        ps5Controller.cross()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF3_LEFT_POSE))
                    )
            )
        ps5Controller.L2()
            .and(
                ps5Controller.circle()
                    .and(
                        ps5Controller.cross()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF3_RIGHT_POSE))
                    )
            )
        ps5Controller.L1()
            .and(
                ps5Controller.cross()
                    .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF4_LEFT_POSE))
            )
        ps5Controller.L2()
            .and(
                ps5Controller.cross()
                    .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF4_RIGHT_POSE))
            )
        ps5Controller.L1()
            .and(
                ps5Controller.cross()
                    .and(
                        ps5Controller.square()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF5_LEFT_POSE))
                    )
            )
        ps5Controller.L2()
            .and(
                ps5Controller.cross()
                    .and(
                        ps5Controller.square()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF5_RIGHT_POSE))
                    )
            )
        ps5Controller.L1()
            .and(
                ps5Controller.square()
                    .and(
                        ps5Controller.triangle()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF6_LEFT_POSE))
                    )
            )
        ps5Controller.L2()
            .and(
                ps5Controller.square()
                    .and(
                        ps5Controller.triangle()
                            .whileTrue(AutoAlignCommand.pathFind(AutoAlignConstantsNew.ALIGN_REEF6_RIGHT_POSE))
                    )
            )


        //xBoxController.leftTrigger().onTrue(AutonIntakeLoop())
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

//
//
//    enum class Side { LEFT, RIGHT }
//    fun findClosestReefFace(direction: Side): Pose2d {
//        var currentPose: () -> Pose2d = {drive!!.pose}
//        val ReefFaces = AutoAlignConstantsNew.LIST_OF_REEF_FACE_POSE
//        val RightReefSticks = AutoAlignConstantsNew.RIGHT_REEF_STICK_POSE
//        val LeftReefSticks = AutoAlignConstantsNew.LEFT_REEF_STICK_POSE
//        var ClosestReefFacePosInList: Int =
//            ReefFaces.indexOf(
//            ReefFaces.minByOrNull { reef ->
//                var dx = reef.translation.x - currentPose.translation.x
//                var dy = reef.translation.y - currentPose.translation.y
//                hypot(dx, dy)
//            } ?: print("uh oh")
//        )
//        if (direction == Side.LEFT) {
//            System.out.println("HELOOOOOOOOO")
//            System.out.println(ClosestReefFacePosInList)
//            System.out.println("CURRENT POSE: $currentPose")
//            println(ClosestReefFacePosInList)
//            return LeftReefSticks[ClosestReefFacePosInList]
//        } else {
//            System.out.println("HELOOOOOOOOO")
//            System.out.println(ClosestReefFacePosInList)
//            println("HELOOOOOOOOO")
//            println(ClosestReefFacePosInList)
//            System.out.println("CURRENT POSE: $currentPose")
//            return RightReefSticks[ClosestReefFacePosInList]
//
//        }
//    }
//
//
//
//


//    fun findClosestRightReefStick(): Pose2d {
//        val currentPose = drive!!.pose
//        val rightReefSticks = AutoAlignConstantsNew.RIGHT_REEF_STICK_POSE
//        return rightReefSticks.minByOrNull { reef ->
//            val dx = reef.translation.x - currentPose.translation.x
//            val dy = reef.translation.y - currentPose.translation.y
//            hypot(dx, dy)
//        } ?: rightReefSticks.first()
//    }
}

