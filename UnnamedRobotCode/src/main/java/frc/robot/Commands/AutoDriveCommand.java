package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.TankDriveSubsystem;

public class AutoDriveCommand extends CommandBase{
    private double leftSide, rightSide, seconds, start;

    public AutoDriveCommand(double leftSide, double rightSide, double seconds) {
        addRequirements(TankDriveSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        start = System.currentTimeMillis();
        TankDriveSubsystem.getInstance().drive(leftSide, rightSide);
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - start) < seconds * 1000;
    }

    @Override
    public void end(boolean interrupted) {
        TankDriveSubsystem.getInstance().drive(0, 0);
    }
}
