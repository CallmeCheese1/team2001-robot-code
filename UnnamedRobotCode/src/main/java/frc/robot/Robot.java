// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.AutoDriveBackwards;
import frc.robot.Subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private TankDriveSubsystem m_drive;
  private XboxController m_controller;
  
  private final SendableChooser<String> m_chooser = new SendableChooser<>();



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // If the robot has more than one motor on each side (which it likely will), the motors have to be described in the code and then grouped.
    //There's gonna be a bunch of errors here because some of this either isn't imported or doesn't match what it'll actually be named. This is just a guide until we get the specifics down.
    // https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/motorcontrol/package-summary.html

    //To use a PWN Motor on its own, you simply create it as an object with the port as the constructor input.
    Spark EXAMPLE = new Spark(5);
    //spark.set(-0.75); is how you set the motor to move in teleop, between 1 and -1 as a percentage.

    //Left side 
    Spark m_frontLeft = new Spark(2);
    Spark m_rearLeft = new Spark(1);

    //Right side
    Spark m_frontRight = new Spark(4);
    Spark m_rearRight = new Spark(3);

    //Differential Drive connects the left side group and right side group defined above. It's the main class used for drivetrains that *aren't* Mecanum.
    //More info: https:  //docs.wpilib.org/en/stable/docs/software/hardware-apis/motors/wpi-drive-classes.html#multi-motor-differentialdrive-with-motorcontrollergroups
    //Docs: https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/DifferentialDrive.html
    m_drive = new TankDriveSubsystem(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);
    
    //The class here depends on the exact type of controller we're using.
    // More info: https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
    // Docs: https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
    m_controller = new XboxController(0);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    AutoDriveBackwards command = new AutoDriveBackwards();
    command.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Arcade drive with a given forward and turn rate
    m_drive.drive(m_controller.getRightY() * 0.8, m_controller.getLeftY() * 0.8);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
