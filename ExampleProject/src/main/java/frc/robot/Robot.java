// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // If the robot has more than one motor on each side (which it likely will), the motors have to be described in the code and then grouped.
  //There's gonna be a bunch of errors here because some of this either isn't imported or doesn't match what it'll actually be named. This is just a guide until we get the specifics down.
  // https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/motorcontrol/package-summary.html

  //To use a PWN Motor on its own, you simply create it as an object with the port as the constructor input.
  Spark EXAMPLE = new Spark(5);
  //spark.set(-0.75); is how you set the motor to move in teleop, between 1 and -1 as a percentage.

  //Left side group
  Spark m_frontLeft = new Spark(1);
  Spark m_rearLeft = new Spark(2);
  MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

  //Right side group
  Spark m_frontRight = new Spark(3);
  Spark m_rearRight = new Spark(4);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

  //Differential Drive connects the left side group and right side group defined above. It's the main class used for drivetrains that *aren't* Mecanum.
  //More info: https://docs.wpilib.org/en/stable/docs/software/hardware-apis/motors/wpi-drive-classes.html#multi-motor-differentialdrive-with-motorcontrollergroups
  //Docs: https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/DifferentialDrive.html
  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
  
  //The class here depends on the exact type of controller we're using.
  // More info: https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
  // Docs: https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
  private final XboxController m_controller = new XboxController(0);

  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_right.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_drive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_drive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // Tank drive with a given left and right rates
    m_drive.tankDrive(m_controller.getLeftY(), m_controller.getRightX());

    // Arcade drive with a given forward and turn rate
    m_drive.arcadeDrive(m_controller.getLeftY(), m_controller.getRightX());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
