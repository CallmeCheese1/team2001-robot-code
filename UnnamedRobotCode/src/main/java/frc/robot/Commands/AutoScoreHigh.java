package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.ClawArmSubsystem;
import frc.robot.Subsystems.TankDriveSubsystem;

public class AutoScoreHigh extends SequentialCommandGroup {
    public AutoScoreHigh(ClawArmSubsystem armIntake) {
        addCommands(new MoveClaw(0.4).withTimeout(1.7));
        addCommands(new MoveArm(0.6).withTimeout(1.5));
        addCommands(new MoveClaw(-0.3).withTimeout(1.3));
        addCommands(new MoveClaw(0).withTimeout(1.0));
    }
}