package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.ClawArmSubsystem;

public class MoveArm extends CommandBase{
    private double Direction;

    public MoveArm(double Direction) {
        addRequirements(ClawArmSubsystem.getInstance());
        this.Direction = Direction;
        System.out.println(Direction);
    }

    @Override
    public void initialize() {
        ClawArmSubsystem.getInstance().armRotate(Direction);
    }

    @Override
    public void end(boolean interrupted) {
        ClawArmSubsystem.getInstance().armRotate(0);
    }
}