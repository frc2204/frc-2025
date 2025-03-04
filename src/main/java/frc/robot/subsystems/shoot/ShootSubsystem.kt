package frc.robot.subsystems.shoot

import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.sun.jdi.ShortType
import config.IntakeConstants
import config.ShootConstants
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.robot.subsystems.intake.IntakeSparkMAXConfig
import edu.wpi.first.wpilibj.Timer

object ShootSubsystem : SubsystemBase() {
 var desiredAngle:Double = ShootConstants.ALGAE_INTAKE_ANGLE
 var AIangle:Double = 0.0 // Make this the Actual Angle updated from Encoder
 val timer = Timer()
 val angleAI = TalonFX(0, "rio")
 val shootMotor1 = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
  configure(IntakeSparkMAXConfig.intakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
 }
val shootMotor2 = SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless).apply {
    configure(IntakeSparkMAXConfig.intakeMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
}

 fun shoot(){
       shootMotor1.set(ShootConstants.SHOOT_SPEED)
       shootMotor2.set(ShootConstants.SHOOT_SPEED)
       timer.reset()
       timer.start()
  //The motor ceases spinning after set amount of time (seconds)
       if (timer.hasElapsed(5.0)){
       shootMotor1.set(0.0)
       shootMotor2.set(0.0)
       timer.stop()
  }
 }


    fun homeAI(Angle:Double){
        desiredAngle = Angle

    }
    override fun periodic(){
        angleAI.setPosition(desiredAngle)
    }
}