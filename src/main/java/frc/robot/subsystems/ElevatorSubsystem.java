package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ElevatorConstants;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor = 
        new TalonFX(ElevatorConstants.kMotorCanId, CANBus.roboRIO());

        private final DigitalInput lowerLimit =
        new DigitalInput(ElevatorConstants.kLowerLimitDioPort);

        private final DigitalInput upperLimit =
        new DigitalInput(ElevatorConstants.kUpperLimitDioPort);

        private final DutyCycleOut motorRequest = new DutyCycleOut(0);

    public ElevatorSubsystem(){
        elevatorMotor.setNeutralMode(NeutralModeValue.Brake);
    
    }
    public boolean isAtLowerLimit() {
        return lowerLimit.get();
    }
    public boolean isAtUpperLimit(){
        return upperLimit.get();
    }

     //üst limite gelindiğinde asansörün durması sağlanıyor
       public void moveUp() {
        if (isAtUpperLimit()) {
            stop();
            return;
        }
         elevatorMotor.setControl(motorRequest.withOutput(ElevatorConstants.kUpOutput));
    }
    //alt limite gelindiğinde asansörün durması sağlanıyor
       public void moveDown() {
        if (isAtLowerLimit()) {
            stop();
            return;
        }
         elevatorMotor.setControl(motorRequest.withOutput(ElevatorConstants.kDownOutput));
    }

    public void stop() {
        elevatorMotor.setControl(motorRequest.withOutput(0));
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Elevator/LowerLimit", isAtLowerLimit());
        SmartDashboard.putBoolean("Elevator/UpperLimit", isAtUpperLimit());
    }

}
