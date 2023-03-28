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

    public void drive(double leftSide, double rightSide){
        m_drive.tankDrive(leftSide, rightSide);
    }


    public TankDriveSubsystem(Spark frontLeft, Spark rearLeft, Spark frontRight, Spark rearRight) {
        //Left side group
        MotorControllerGroup m_left = new MotorControllerGroup(frontLeft, rearLeft);

        //Right side group
        MotorControllerGroup m_right = new MotorControllerGroup(frontRight, rearRight);
        m_left.setInverted(true);
        m_drive = new DifferentialDrive(m_left, m_right);
        instance = this;
    }
}
