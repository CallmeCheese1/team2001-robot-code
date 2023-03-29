package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawArmSubsystem extends SubsystemBase {
    //TODO: Create the ClawArmSubsystem to control the two motors that will make up the claw arm. It'll need two motors in the constructor.

    private TalonSRX m_arm;
    private TalonSRX m_claw;

    public static ClawArmSubsystem instance = null;

    public static ClawArmSubsystem getInstance() {
        return instance;
    }

    public void armRotate(double direction){
        m_arm.set(ControlMode.PercentOutput, direction);
    }

    public void grab(double gripDirection){
        m_claw.set(ControlMode.PercentOutput, gripDirection);
    }

    public ClawArmSubsystem(TalonSRX arm_motor, TalonSRX claw_motor) {
        m_arm = arm_motor;
        m_claw = claw_motor;

        instance = this;
    }

}
