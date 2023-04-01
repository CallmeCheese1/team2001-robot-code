package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.ClawArmSubsystem;

public class MoveClaw extends CommandBase{
    private double gripDirection;
    private double doubleGripDirection = 0;

    public MoveClaw(DoubleSupplier Direction, boolean leftTrigger) {
        addRequirements(ClawArmSubsystem.getInstance());
        System.out.println("HELLO THERE Direction.getAsDouble() = " + Direction.getAsDouble());
        if (leftTrigger) {
            this.gripDirection = Direction.getAsDouble() * -1;
        } else {
            this.gripDirection = Direction.getAsDouble();
        }
    }

    public MoveClaw(double doubleGripDirection) {
        addRequirements(ClawArmSubsystem.getInstance());
        this.doubleGripDirection = doubleGripDirection;
    }

    @Override
    public void initialize() {
        ClawArmSubsystem.getInstance().grab(doubleGripDirection);

        /*if (doubleGripDirection == 0) {
            
            System.out.println("calling gripDirection" + gripDirection);
        } else {
            ClawArmSubsystem.getInstance().grab(doubleGripDirection);
            System.out.println("Calling doubleGripDirection" + doubleGripDirection);
        }*/
    }

    @Override
    public void end(boolean interrupted) {
        ClawArmSubsystem.getInstance().grab(0);
    }
}