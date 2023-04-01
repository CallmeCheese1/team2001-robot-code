package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.TankDriveSubsystem;

public class AutoDriveForwards extends SequentialCommandGroup {
    public AutoDriveForwards(TankDriveSubsystem driveSubsystem) {
        System.out.println("hello there");
        addCommands(new AutoDriveCommand(driveSubsystem, 0.7, 0.7, 2));
    }
}