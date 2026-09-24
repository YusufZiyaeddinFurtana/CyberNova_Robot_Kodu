package frc.robot.subsystems;

    import com.ctre.phoenix6.CANBus;
    import com.ctre.phoenix6.hardware.CANcoder;
    import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkLowLevel.MotorType;
    import com.revrobotics.spark.SparkMax;
    import edu.wpi.first.math.MathUtil;
    import com.ctre.phoenix6.controls.DutyCycleOut;
    import edu.wpi.first.math.controller.PIDController;
    import edu.wpi.first.math.geometry.Rotation2d;
    import edu.wpi.first.math.kinematics.SwerveModuleState;
    import frc.robot.Constants.DriveConstants;
    import com.revrobotics.ResetMode;
    import com.revrobotics.PersistMode;
    import com.revrobotics.spark.config.SparkMaxConfig;
    import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;   
    
    public class SwerveModule {
        private final double angleOffsetDegrees;
        private final TalonFX driveMotor;
        private final SparkMax steerMotor;
        private final CANcoder angleEncoder;

        private final DutyCycleOut driveRequest = new DutyCycleOut(0);
        private final PIDController steeringController =
            new PIDController(DriveConstants.kSteerKp, 0, 0);

        public SwerveModule(int driveId, int steerId, int encoderId, double angleOffsetDegrees) {
            driveMotor = new TalonFX(driveId, CANBus.roboRIO());
            steerMotor = new SparkMax(steerId, MotorType.kBrushless);
            angleEncoder = new CANcoder(encoderId, CANBus.roboRIO());
            this.angleOffsetDegrees = angleOffsetDegrees;

            steeringController.enableContinuousInput(0, 360);

            SparkMaxConfig steerConfig = new SparkMaxConfig();
            steerConfig.smartCurrentLimit(DriveConstants.kSteerCurrentLimitAmps);
            steerConfig.idleMode(IdleMode.kBrake);

            steerMotor.configure(
                steerConfig,
                 ResetMode.kResetSafeParameters,
                 PersistMode.kNoPersistParameters);
    
        driveMotor.setNeutralMode(NeutralModeValue.Brake);
    }
   public double getRawAngleDegrees() {
    return angleEncoder.getAbsolutePosition().getValueAsDouble() * 360.0;
}

public double getAngleDegrees() {
    return MathUtil.inputModulus(
        getRawAngleDegrees() - angleOffsetDegrees, 0.0, 360.0);
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        if (Math.abs(desiredState.speedMetersPerSecond) < 0.02) {
            stop();
            return;
        }

        Rotation2d currentAngle = Rotation2d.fromDegrees(getAngleDegrees());
        desiredState.optimize(currentAngle);

        double targetAngleDegrees = MathUtil.inputModulus(
            desiredState.angle.getDegrees(), 0, 360
        );
        double steerOutput = steeringController.calculate(
        getAngleDegrees(), targetAngleDegrees
     );
    steerMotor.set(MathUtil.clamp(
        steerOutput,
        -DriveConstants.kMaxSteerDutyCycle,
        DriveConstants.kMaxSteerDutyCycle
        ));

    double driveOutput =
        desiredState.speedMetersPerSecond
        / DriveConstants.kMaxModuleSpeedMPS;

    driveMotor.setControl(driveRequest.withOutput(MathUtil.clamp(
        driveOutput,
        -DriveConstants.kMaxDriveDutyCycle,
        DriveConstants.kMaxDriveDutyCycle
            )
        )
    );
}

public void stop() {
    driveMotor.setControl(driveRequest.withOutput(0));
    steerMotor.stopMotor();
}
        
    }

