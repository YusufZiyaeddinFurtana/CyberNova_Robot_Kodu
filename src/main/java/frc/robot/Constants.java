// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
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

    //DIO 0 alt DIO 1 üst sınır ise

    public static final int kLowerLimitDioPort = 0;
    public static final int kUpperLimitDioPort = 1;
  }
}