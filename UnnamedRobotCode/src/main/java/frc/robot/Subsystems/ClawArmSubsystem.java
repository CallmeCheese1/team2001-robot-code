package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawArmSubsystem extends SubsystemBase {
    //TODO: Create the ClawArmSubsystem to control the two motors that will make up the claw arm. It'll need two motors in the constructor.

    private Spark m_arm;
    private Spark m_claw;

    public static ClawArmSubsystem instance = null;

    public static ClawArmSubsystem getInstance() {
        return instance;
    }

    public void armRotate(double direction){
        m_arm.set(direction);
    }

    public void grab(double gripDirection){
        m_claw.set(gripDirection);
    }

    public ClawArmSubsystem(Spark arm_motor, Spark claw_motor) {
        m_arm = arm_motor;
        m_claw = claw_motor;

        instance = this;
    }

}
