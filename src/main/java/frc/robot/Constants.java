// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

  public static class AutoConstants {
    public static final double kForwardSpeedMPS = 0.5;
    public static final double kForwardTimeSeconds = 4.0;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;

    public static final double kDriveDeadband = 0.08;

    public static final int kElevatorUpButton = 3;
    public static final int kElevatorDownButton = 4;
  }

public static class IntakeConstants {
  
  // geçiçi can ID
  public static final int kCurrentLimitAmps = 20;
  // Geçici içeri alma çıkışı: %30
  public static final double kIntakeOut = 0.30;
  // Geçici dışarı verme çıkışı (ters yönlü)
  public static final double kEjectOutput = -0.30;
  
  //Geçici ayarlar
public static final int kLeftMotorCanId = 14;
public static final int kRightMotorCanId = 15;

public static final boolean kLeftMotorInverted = false;
public static final boolean kRightMotorInverted = true;
  }
  public static class ElevatorConstants {
    public static final int kMotorCanId = 13;

    public static final double kUpOutput = 0.15; 
    public static final double kDownOutput = -0.15;

    //DIO 0 alt DIO 1 üst sınır ise

    public static final int kLowerLimitDioPort = 0;
    public static final int kUpperLimitDioPort = 1;
  }
  public static class DriveConstants {

    public static final int kSteerCurrentLimitAmps = 20;

    public static final double kMaxAngularSpeedRPS = 1.0;
    //Radians Per Second

    public static final double kWheelbaseMeters = 0.633;
    public static final double kTrackWidthMeters = 0.633;

    public static final double kMaxModuleSpeedMPS = 1.0;
    // Meters Per Second
public static final double kMaxDriveDutyCycle = 0.20;
public static final double kMaxSteerDutyCycle = 0.20;
public static final double kSteerKp = 0.005;

    public static final int kFrontLeftDriveId = 1;
    public static final int kFrontRightDriveId = 2;
    public static final int kRearLeftDriveId = 3;
    public static final int kRearRightDriveId = 4;

    public static final int kFrontLeftSteerId = 5;
    public static final int kFrontRightSteerId = 6;
    public static final int kRearLeftSteerId = 7;
    public static final int kRearRightSteerId = 8;

    public static final int kFrontLeftEncoderId = 9;
    public static final int kFrontRightEncoderId = 10;
    public static final int kRearLeftEncoderId = 11;
    public static final int kRearRightEncoderId = 12;

    public static final int kPigeonId = 20;

    public static final double kFrontLeftOffsetDegrees = 0.0;
    public static final double kFrontRightOffsetDegrees = 0.0;
    public static final double kRearLeftOffsetDegrees = 0.0;
    public static final double kRearRightOffsetDegrees = 0.0;
  }
}
