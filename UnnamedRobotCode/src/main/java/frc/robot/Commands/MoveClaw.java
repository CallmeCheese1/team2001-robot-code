package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.ClawArmSubsystem;

public class MoveClaw extends CommandBase{
    private double gripDirection;

    public MoveClaw(double Direction) {
        addRequirements(ClawArmSubsystem.getInstance());
        this.gripDirection = Direction;
    }

    @Override
    public void initialize() {
        ClawArmSubsystem.getInstance().grab(gripDirection);
    }

    @Override
    public void end(boolean interrupted) {
        ClawArmSubsystem.getInstance().grab(0);
    }
}