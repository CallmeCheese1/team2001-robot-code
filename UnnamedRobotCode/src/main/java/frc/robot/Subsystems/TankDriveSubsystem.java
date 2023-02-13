package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {
    private DifferentialDrive m_drive;


    private static TankDriveSubsystem instance = null;

    public static TankDriveSubsystem getInstance() {
        return instance;
    }

    public void drive(double rightSide, double leftSide){
        m_drive.tankDrive(rightSide, leftSide);
    }


    public TankDriveSubsystem(Spark frontLeft, Spark rearLeft, Spark frontRight, Spark rearRight) {
        //Left side group
        MotorControllerGroup m_left = new MotorControllerGroup(frontLeft, rearLeft);

        //Right side group
        MotorControllerGroup m_right = new MotorControllerGroup(frontRight, rearRight);
        m_right.setInverted(true);
        m_drive = new DifferentialDrive(m_left, m_right);
        instance = this;
    }
}


/*
 * //Left side group
  Spark m_frontLeft = new Spark(2);
  Spark m_rearLeft = new Spark(1);
  MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

  //Right side group
  Spark m_frontRight = new Spark(4);
  Spark m_rearRight = new Spark(3);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
 * 
 */