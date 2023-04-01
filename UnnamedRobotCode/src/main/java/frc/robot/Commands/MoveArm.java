package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.ClawArmSubsystem;

public class MoveArm extends CommandBase{
    private DoubleSupplier Direction;
    private double doubleDirection = 0;

    public MoveArm(DoubleSupplier Direction) {
        addRequirements(ClawArmSubsystem.getInstance());
        this.Direction = Direction;
    }

    public MoveArm(double doubleDirection) {
        addRequirements(ClawArmSubsystem.getInstance());
        this.doubleDirection = doubleDirection;
        System.out.println(Direction);
    }

    @Override
    public void initialize() {
        if (doubleDirection == 0) {
            ClawArmSubsystem.getInstance().armRotate(Direction.getAsDouble());
        } else {
            ClawArmSubsystem.getInstance().armRotate(doubleDirection);
        }
    }

    @Override
    public void end(boolean interrupted) {
        ClawArmSubsystem.getInstance().armRotate(0);
    }
}