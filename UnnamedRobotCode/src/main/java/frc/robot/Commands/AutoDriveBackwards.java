package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoDriveBackwards extends SequentialCommandGroup {
    public AutoDriveBackwards() {
        addCommands(new AutoDriveCommand(-0.7, -0.7, 1));
    }
}