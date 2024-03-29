package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.io.File;
import frc.robot.Commands.CV.*;

import frc.robot.Subsystems.*;

public class RobotContainer {

  private final SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
      "JsonConstants"));
  final Joystick WakakeController = new Joystick(0);

  public RobotContainer() {
    configureBindings();

    Command driveSwerve = swerve.driveCommand(
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(1), Constants.ControllerDeadband),
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(0), Constants.ControllerDeadband),
        () -> getAsInt(WakakeController.getRawButton(5)) - getAsInt(WakakeController.getRawButton(6)), false,
        () -> true);

    swerve.setDefaultCommand(driveSwerve);

  }

  private void configureBindings() {

    new JoystickButton(WakakeController, 3).whileTrue(Commands.runOnce(swerve::zeroGyro));
    new JoystickButton(WakakeController, 11).whileTrue(new NoteAlign(swerve));
    new JoystickButton(WakakeController, 2).whileTrue(new SpeakerAlign(swerve));
  }

  public Command getAutonomousCommand() {
    return swerve.getAutonomousCommand("Three note");
  }

  public double getAsInt(boolean bollean) {
    if (bollean) {
      return 0.8;
    } else {
      return 0;
    }
  }

}
