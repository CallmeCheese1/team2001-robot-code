// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.AutoDriveForwards;
import frc.robot.Commands.AutoScoreHigh;
import frc.robot.Commands.MoveArm; 
import frc.robot.Commands.MoveClaw;
import frc.robot.Subsystems.ClawArmSubsystem;
import frc.robot.Subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.CAN;

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
  private ClawArmSubsystem m_clawarm;
  private XboxController m_controller;

  private Trigger aButton;
  private Trigger bButton;
  private Trigger xButton;
  private Trigger yButton;

  private Trigger rightBumper;
  private Trigger leftBumper;
  
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

    //These are apparently gonna be redline motors, which need to have the voltage regulated so they don't burn out.
    TalonSRX m_arm = new TalonSRX(0); //ON THE ACTUAL ROBOT, ARM IS ID 0, IT'S ACTUALLY CONNECTED TO THE CLAW, AND IT NEEDS TO BE AT 70%
    TalonSRX m_claw = new TalonSRX(1); //ON THE ACTUAL ROBOT, CLAW IS ID 1, IT'S CONNECTED TO THE ARM, AND IT NEEDS TO BE AT 30%

    m_clawarm = new ClawArmSubsystem(m_arm, m_claw);
    
    //The class here depends on the exact type of controller we're using.
    // More  info: https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
    // Docs: https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
    m_controller = new XboxController(0);

    aButton = new JoystickButton(m_controller, Button.kA.value);
    aButton.whileTrue(new MoveArm(0.65)); //See below comment. Despite what the variable is named, Arm actually controls the Claw.

    bButton = new JoystickButton(m_controller, Button.kB.value);
    bButton.whileTrue(new MoveArm(-0.65)); 

    //rightTrigger = new Trigger(() -> (m_controller.getRightTriggerAxis() > 0.1));
    //rightTrigger.whileTrue(new MoveClaw(m_controller::getRightTriggerAxis, false));

   
    //leftTrigger = new Trigger(() -> (m_controller.getLeftTriggerAxis() > 0.1));
    //leftTrigger.whileTrue(new MoveClaw(m_controller::getLeftTriggerAxis, true));

    //note: the directions are actually reversed, so ignore the names. What's said to control the Claw is actually wired to control the Arm, and the Arm needs to be at 30% speed.

    leftBumper = new JoystickButton(m_controller, Button.kLeftBumper.value).whileTrue(new MoveClaw(0.3));
    rightBumper = new JoystickButton(m_controller, Button.kLeftBumper.value).whileTrue(new MoveClaw(-0.3));

    xButton = new JoystickButton(m_controller, Button.kX.value);
    xButton.whileTrue(new MoveClaw(0.3));
    yButton = new JoystickButton(m_controller, Button.kY.value);
    yButton.whileTrue(new MoveClaw(-0.3)); 
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

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
    //AutoDriveForwards command = new AutoDriveForwards(m_drive);
    //command.schedule();
    AutoScoreHigh score = new AutoScoreHigh(m_clawarm);
    score.schedule();
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
    // Tank drive with the right side and the left side. Multiply by decimals to limit speed.
    m_drive.drive(m_controller.getLeftY() * 0.9, m_controller.getRightY() * 0.9);
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
