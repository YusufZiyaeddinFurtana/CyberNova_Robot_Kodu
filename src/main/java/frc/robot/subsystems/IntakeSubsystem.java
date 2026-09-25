package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
  
public class IntakeSubsystem extends SubsystemBase{

    //Intake motor sürücüsünü CAN ID ile tanımlamak için
    private final SparkMax leftIntakeMotor = new SparkMax(
        IntakeConstants.kLeftMotorCanId,
        MotorType.kBrushless
    );
      private final SparkMax rightIntakeMotor = new SparkMax(
        IntakeConstants.kRightMotorCanId,
        MotorType.kBrushless
    );
    public IntakeSubsystem() {
        //Başlangıç ayarları
      SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig.inverted(IntakeConstants.kLeftMotorInverted);
    leftConfig.smartCurrentLimit(IntakeConstants.kCurrentLimitAmps);
    leftConfig.idleMode(IdleMode.kBrake);

        SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.inverted(IntakeConstants.kRightMotorInverted);
    rightConfig.smartCurrentLimit(IntakeConstants.kCurrentLimitAmps);
    rightConfig.idleMode(IdleMode.kBrake);

        //Ayarları uygulamak için
       leftIntakeMotor.configure(
    leftConfig,
    ResetMode.kResetSafeParameters,
    PersistMode.kNoPersistParameters
    );

        rightIntakeMotor.configure(
    rightConfig,
    ResetMode.kResetSafeParameters,
    PersistMode.kNoPersistParameters
        );
    }
    public void takeIn(){
        //Küpü içeri almak için motor çıkışını uygular
        leftIntakeMotor.set(IntakeConstants.kIntakeOut);
        rightIntakeMotor.set(IntakeConstants.kIntakeOut);
    }
    public void eject() {
        //küpü fırlatmak için motoru ters yönde çalıştırır
        leftIntakeMotor.set(IntakeConstants.kEjectOutput);
        rightIntakeMotor.set(IntakeConstants.kEjectOutput);
    }
    public void stop() {
        //işlem sonrası motor çıkışını sıfırlar
       leftIntakeMotor.stopMotor();
        rightIntakeMotor.stopMotor();
    }
}
