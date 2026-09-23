// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // Robot susbsystem ve command'lerinin tanımlandığı bölüm

  private final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);

 // subsystemlar, OI cihazları, ve command'leri tutan robot container'ı

  private final IntakeSubsystem intake = new IntakeSubsystem();

  public RobotContainer() {
    configureBindings();
  }

    private void configureBindings() {
   m_driverController.button(1).whileTrue(
      intake.startEnd(
        () -> intake.takeIn(),
        () -> intake.stop()
      )
    );
    m_driverController.button(2).whileTrue(
      intake.startEnd(
        () -> intake.eject(),
        () -> intake.stop())
    );
  }

  /**
   * Use this method to define your ;trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Replace with the robot's autonomous command when ready.
    return Commands.none();
  }
}
