// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.MathUtil;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RobotContainer {
  // Robot susbsystem ve command'lerinin tanımlandığı bölüm

  private final DriveSubsystem drive = new DriveSubsystem();
  
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  private final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);

 // subsystemlar, OI cihazları, ve command'leri tutan robot container'ı

  private final IntakeSubsystem intake = new IntakeSubsystem();

  private boolean fieldRelative = false;

  private final ElevatorSubsystem elevator = new ElevatorSubsystem();

  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(
      drive.runEnd(this::driveWithJoystick, drive::stop)
    );
    autoChooser.setDefaultOption("Bekle", Commands.none());
    autoChooser.addOption("Auto Line denemesi", createAutoLineCommand());
    SmartDashboard.putData("Autonomous", autoChooser);
  }

  private void driveWithJoystick() {
  if (!DriverStation.isTeleopEnabled()) {
    drive.stop();
    return;
  }

  double forward = -MathUtil.applyDeadband(
      m_driverController.getY(), OperatorConstants.kDriveDeadband)
      * DriveConstants.kMaxModuleSpeedMPS;

  double left = -MathUtil.applyDeadband(
      m_driverController.getX(), OperatorConstants.kDriveDeadband)
      * DriveConstants.kMaxModuleSpeedMPS;

  double rotation = -MathUtil.applyDeadband(
      m_driverController.getZ(), OperatorConstants.kDriveDeadband)
      * DriveConstants.kMaxAngularSpeedRPS;

  if (fieldRelative) {
    drive.driveFieldRelative(forward, left, rotation);  
  }else{
    drive.driveRobotRelative(forward, left, rotation);
  }
  SmartDashboard.putBoolean("Swerve/FieldRelative", fieldRelative);
}

  private void configureBindings() {
  teleopButton(1).whileTrue(
      intake.startEnd(intake::takeIn, intake::stop)
  );

  teleopButton(2).whileTrue(
      intake.startEnd(intake::eject, intake::stop)
  );

  teleopButton(OperatorConstants.kElevatorUpButton).whileTrue(
      elevator.runEnd(elevator::moveUp, elevator::stop)
  );

  teleopButton(OperatorConstants.kElevatorDownButton).whileTrue(
      elevator.runEnd(elevator::moveDown, elevator::stop)
  );

  teleopButton(5).onTrue(
      Commands.runOnce(() -> fieldRelative = !fieldRelative)
  );

  teleopButton(6).onTrue(
      Commands.runOnce(drive::zeroHeading, drive)
  );
}

private Trigger teleopButton(int buttonNumber) {
    return m_driverController.button(buttonNumber)
    .and(DriverStation::isTeleopEnabled);
  }

  private Command createAutoLineCommand() {
    return drive.runEnd(
      () -> drive.driveRobotRelative(
        AutoConstants.kForwardSpeedMPS, 0.0, 0.0),
        drive::stop
        ).withTimeout(AutoConstants.kForwardTimeSeconds);
  }
  public Command getAutonomousCommand(){
    return autoChooser.getSelected();
  }
}
