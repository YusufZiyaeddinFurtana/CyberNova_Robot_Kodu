package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;


import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;


public class DriveSubsystem extends SubsystemBase{

    private final SwerveModule frontLeft = new SwerveModule(
        DriveConstants.kFrontLeftDriveId,    
        DriveConstants.kFrontLeftSteerId,
        DriveConstants.kFrontLeftEncoderId,
        DriveConstants.kFrontLeftOffsetDegrees
    );
      private final SwerveModule frontRight = new SwerveModule(
        DriveConstants.kFrontRightDriveId,
        DriveConstants.kFrontRightSteerId,
        DriveConstants.kFrontRightEncoderId,
        DriveConstants.kFrontRightOffsetDegrees
    );
    private final SwerveModule rearLeft = new SwerveModule(
        DriveConstants.kRearLeftDriveId,
        DriveConstants.kRearLeftSteerId,
        DriveConstants.kRearLeftEncoderId,
        DriveConstants.kRearLeftOffsetDegrees
    );

    private final SwerveModule rearRight = new SwerveModule(
        DriveConstants.kRearRightDriveId,
        DriveConstants.kRearRightSteerId,
        DriveConstants.kRearRightEncoderId,
        DriveConstants.kRearRightOffsetDegrees
    );

    public void driveFieldRelative(
        double forwardMetersPerSecond,
        double leftMetersPerSecond,
        double counterClockwiseRPS
    ) {
        ChassisSpeeds robotSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds( // Sürücüye göre(field relative) hareket komutlarını robot merkezli(robot relative) hareket komutlarına çevirir
            forwardMetersPerSecond,
            leftMetersPerSecond,
            counterClockwiseRPS,
            pigeon.getRotation2d()
        );

    driveRobotRelative(robotSpeeds.vxMetersPerSecond,
    robotSpeeds.vyMetersPerSecond,
    robotSpeeds.omegaRadiansPerSecond
    );
    }

    public void zeroHeading() {
        pigeon.setYaw(0.0);
    }

    private final Pigeon2 pigeon =
    new Pigeon2(DriveConstants.kPigeonId, CANBus.roboRIO());

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        new Translation2d(
           DriveConstants.kWheelbaseMeters / 2,
        DriveConstants.kTrackWidthMeters / 2
        ), // sol ön
        new Translation2d(
            DriveConstants.kWheelbaseMeters / 2,
            -DriveConstants.kTrackWidthMeters / 2
        ), // sağ ön
        new Translation2d(
            -DriveConstants.kWheelbaseMeters / 2,
            DriveConstants.kTrackWidthMeters / 2
        ), // sol arka
        new Translation2d(
            -DriveConstants.kWheelbaseMeters / 2,
            -DriveConstants.kTrackWidthMeters / 2
        )
        ); // sağ arka
    
    public SwerveModuleState[] calculateModuleStates(
        double forwardMetersPerSecond,
        double leftMetersPerSecond,
        double counterClockwiseRPS
    ) {
        ChassisSpeeds robotSpeed = new ChassisSpeeds(
            forwardMetersPerSecond,
            leftMetersPerSecond,
            counterClockwiseRPS
        );
        return kinematics.toSwerveModuleStates(robotSpeed);
    }
    public void driveRobotRelative(
    double forwardMetersPerSecond,
    double leftMetersPerSecond,
    double counterClockwiseRPS
) {
    SwerveModuleState[] states = calculateModuleStates(
        forwardMetersPerSecond,
        leftMetersPerSecond,
        counterClockwiseRPS
    );

    SwerveDriveKinematics.desaturateWheelSpeeds( //Dört tekerin istenen hızlarını ortak üst sınıra ölçekler
        states, DriveConstants.kMaxModuleSpeedMPS
    );

    frontLeft.setDesiredState(states[0]);
    frontRight.setDesiredState(states[1]);
    rearLeft.setDesiredState(states[2]);
    rearRight.setDesiredState(states[3]);
}

public void stop() {
    frontLeft.stop();
    frontRight.stop();
    rearLeft.stop();
    rearRight.stop();
}

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Swerve/FrontLeftAngle", frontLeft.getRawAngleDegrees());
        SmartDashboard.putNumber("Swerve/FrontRightAngle", frontRight.getRawAngleDegrees());
        SmartDashboard.putNumber("Swerve/RearLeftAngle", rearLeft.getRawAngleDegrees());
        SmartDashboard.putNumber("Swerve/RearRightAngle", rearRight.getRawAngleDegrees());
        SmartDashboard.putNumber("Swerve/RobotYaw", pigeon.getYaw().getValueAsDouble());
        SmartDashboard.putNumber("Swerve/FrontLeftAdjusted", frontLeft.getAngleDegrees());
        SmartDashboard.putNumber("Swerve/FrontRightAdjusted", frontRight.getAngleDegrees());
        SmartDashboard.putNumber("Swerve/RearLeftAdjusted", rearLeft.getAngleDegrees());
        SmartDashboard.putNumber("Swerve/RearRightAdjusted", rearRight.getAngleDegrees());
    }
}
