package frc.robot.subsystems.groundintake

import com.ctre.phoenix6.controls.CoastOut
import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import config.ElevatorConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.GroundIntakeSparkMAXConfig
import config.GroundIntakeConstants
import frc.robot.subsystems.elevator.CTREConfig
import frc.robot.subsystems.intake.GroundIntakeSparkMAXConfig.groundIntakeMotorConfig
//import frc.robot.subsystems.elevator.ElevatorSubsystem.elevatorMotor
//import frc.robot.subsystems.elevator.ElevatorSubsystem.angle
import frc.robot.subsystems.intake.GroundIntakeSparkMAXConfig2
import org.littletonrobotics.junction.Logger

object GroundIntakeSubsystem : SubsystemBase() {

    val AIintakemotor1 = SparkMax(GroundIntakeConstants.GROUNDINTAKE_MOTOR_CAN_ID,SparkLowLevel.MotorType.kBrushless).apply{
        configure(GroundIntakeSparkMAXConfig.groundIntakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters, SparkBaseConfig.IdleMode(CoastOut))
    }
    val AIintakemotor2 = SparkMax(GroundIntakeConstants.GROUNDINTAKE_MOTOR_CAN_ID,SparkLowLevel.MotorType.kBrushless).apply{
        configure(GroundIntakeSparkMAXConfig2.groundIntakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }
    val AIarmmotor = TalonFX(GroundIntakeConstants.GROUNDINTAKE_MOTOR_ID, "rio")
    val AIangle get() = AIarmmotor.position.valueAsDouble

    var desiredAngle = 0.0
        set(angle) {
            field = if(angle in ElevatorConstants.ELEVATOR_MIN_HEIGHT..ElevatorConstants.ELEVATOR_MAX_HEIGHT)
                angle
            else
                this.AIangle
        }

    var extensionOffset = 0.5

    init{
        AIarmmotor.configurator.apply(CTREConfig.elevatorFXConfig)
    }

    override fun periodic() {




        AIarmmotor.setPosition(desiredAngle)

        Logger.recordOutput("AIdesiredAngle", desiredAngle)

        Logger.recordOutput("AIAngle", AIangle)
        Logger.recordOutput("AIVelocity", AIarmmotor.velocity.valueAsDouble)
        Logger.recordOutput("AICurrent", AIarmmotor.torqueCurrent.valueAsDouble)
    }
    
    fun stopintake() {
        AIintakemotor2.stopMotor()
        AIintakemotor1.stopMotor()
    }
    
    
    fun intake() {
        AIintakemotor2.set(GroundIntakeConstants.GROUNDINTAKE_SPEED)
        AIintakemotor1.set(GroundIntakeConstants.GROUNDINTAKE_SPEED)
    }

    
    init{
//        AIintakemotor2.follow
    }

}