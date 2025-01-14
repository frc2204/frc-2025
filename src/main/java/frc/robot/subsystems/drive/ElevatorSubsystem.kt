package frc.robot.subsystems.drive

import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.wpilibj.motorcontrol.Talon
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.util.ElevatorConstants


object ElevatorSubsystem : SubsystemBase() {
    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.
    /**
     * Returns the Singleton instance of this ElevatorSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: `ElevatorSubsystem.getInstance();`
     */
    /**
     * The Singleton instance of this ElevatorSubsystem. Code should use
     * the [.getInstance] method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    val instance: ElevatorSubsystem = ElevatorSubsystem()

    val motor = TalonFX(1, CANBus("1"))
    var digit: Int = 0
    val Positions = arrayOf(
        ElevatorConstants.Position.L1,
        ElevatorConstants.Position.L2,
        ElevatorConstants.Position.L3,
        ElevatorConstants.Position.L4
    )
    private var currentPosition = Positions[digit]
    fun getCurrentPosition():ElevatorConstants.Position {
        return currentPosition
    }
    fun moveToNextPosition() {
        motor.setPosition(Positions[digit % 4].Height)
        println(Positions[digit])
        digit++

    }
}

