package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.TankDriveSubsystem;

public class AutoDriveCommand extends CommandBase{
    private double leftSide, rightSide, seconds, start;
    TankDriveSubsystem driveSubsystem;

    public AutoDriveCommand(TankDriveSubsystem drive, double leftSide, double rightSide, double seconds) {
        addRequirements(drive);
        this.driveSubsystem = drive;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.seconds = seconds;
    }

    @Override
    public void initialize() {
        start = System.currentTimeMillis();
        System.out.println("Auto --> init called: " + start + " start time -->");
        System.out.println("Auto-> Left Side: " + leftSide + " Right Side: " + rightSide);    }
    
    @Override
    public void execute() {
        driveSubsystem.drive(leftSide, rightSide);
    }

    @Override
    public boolean isFinished() {
        //System.out.println("Is Finished Called --> Auto");
        return (System.currentTimeMillis() - start) < seconds * 1000;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0, 0);
    }
}
