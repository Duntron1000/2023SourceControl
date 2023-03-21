// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Auton.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.arm.arm;
import edu.wpi.first.cameraserver.CameraServer; 
/*
The VM is configured to automatically run this class, and to call the functions corresponding to
each mode, as described in the TimedRobot documentation. If you change the name of this class or
the package after creating this project, you must also update the build.gradle file in the
project.
*/
public class Robot extends TimedRobot {

  //Objects
  public static DriveTrain drive = new DriveTrain();
  private OIHandler oi = new OIHandler();
  private vision limelightboyo = new vision();
  private balance ballet = new balance(); //see balance class
  private grabber grab = new grabber();
  public static arm armyBoy = new arm();

  //Important variables
  private double tiltsetpoint = 0;
  private double extendsetpoint = 0;
  private boolean done = true;
  private double targetSetpoint = 0;
  private boolean buttonIsPress = false;
  //private boolean failSafeEnable = false;
  private String inSteak;
  private double intakeSpeed = 0;

  //Auton choices
  private static final String kDefaultAuto = "Nothing";
  private static final String kCustomAuto1 = "communityExit";
  private static final String kCustomAuto2 = "oneCone";
  private static final String kCustomAuto3 = "oneCube";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  autonBase autoBoyo;
  
  //Puts all of the data we want onto the smartdashboard
  private void smartdashboards() {

    //gyro
    
    
    //Adds Joysticks values
    SmartDashboard.putNumber("Joystick X: ", oi.getJoystickX());
    SmartDashboard.putNumber("Joystick Y: ", oi.getJoystickY());

    //Adds current extension ammount and angle of the arm
    //SmartDashboard.putNumber("Extension Ammount: ", armyBoy.getExtendEncoder());

    //Encoder values
    SmartDashboard.putNumber("Encoder Left: ", drive.getLeftEncoder());
    SmartDashboard.putNumber("Encoder Right: ", drive.getRightEncoder());

    //Arm angle to be moved to and if it is done moving
    SmartDashboard.putNumber("Target Position: ", targetSetpoint);
    SmartDashboard.putBoolean("Done Moving?: ", done);

    //Setpoints
    SmartDashboard.putNumber("Arm Set Point: ", tiltsetpoint);
    SmartDashboard.putNumber("Extend Set Point: ", extendsetpoint);

    //Pneumatics pressure
    //SmartDashboard.putNumber("Robot Pressure: ", grab.getPressure());
    //Intake state
    if (grab.extended) inSteak = "Cube";
    else inSteak = "Cone";

    SmartDashboard.putString("Intake", inSteak);

    //Intake direcrion
    if (intakeSpeed == .6) SmartDashboard.putString("Intake Direction: ", "Fast Out");
    else if (intakeSpeed == .2) SmartDashboard.putString("Intake Direction: ", "Out");
    else if (intakeSpeed == -.15) SmartDashboard.putString("Intake Direction: ", "In");
    else SmartDashboard.putString("Intake Direction: ", "Stopped");

    //Robot's current angle
    //SmartDashboard.putNumber("Gyro: ", gyroRap((int)(Math.round(gBoy.getAngle()))));
    SmartDashboard.putNumber("Gyro: ", (int)(ballet.navX.getPitch())); //was causing an error 
  }

  /*
  //Returns current angle in the smartdashboard
  private int gyroRap(int angle){
    //If angle is less than zero it will loop back to a positive angle between 0-360
    if (angle < 0) return 360-(int)(Math.abs(ballet.navX.getRoll())%360);
    //Else display current gyro angle between 0-360
    else return angle%360;
  }
  */
  /*
  This function is run when the robot is first started up and should be used for any
  initialization code.
  */
  @Override
  public void robotInit() {

    //smartdashboard
    //Adds auton options
    m_chooser.setDefaultOption("Nothing", kDefaultAuto);
    m_chooser.addOption("Exit Community", kCustomAuto1);
    m_chooser.addOption("One Cone", kCustomAuto2);
    m_chooser.addOption("One Cone Exit", kCustomAuto3);
    SmartDashboard.putData("Auto choices", m_chooser);

    smartdashboards();

    CameraServer.startAutomaticCapture();
  }

  /*
  This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
  that you want ran during disabled, autonomous, teleoperated and test.
  
   <p>This runs after the mode specific periodic functions, but before LiveWindow and
  SmartDashboard integrated updating.
  */
  @Override
  public void robotPeriodic() {
    smartdashboards();
    //armyBoy.extend(extendsetpoint);  
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    if (m_autoSelected.equals(kCustomAuto1))autoBoyo = new autonBase();
    else if (m_autoSelected.equals(kCustomAuto2)) autoBoyo = new autonOneCone();
    else if (m_autoSelected.equals(kCustomAuto3)) autoBoyo = new autonOneConeExit();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    autoBoyo.execute();
    tiltsetpoint = armyBoy.getTiltEncoder();
    extendsetpoint = armyBoy.getExtendEncoder();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    grab.resetEncoders();
    
    limelightboyo.setPipeLine();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //Drives using flighstick values
    drive.drive(oi.getJoystickX(), -oi.getJoystickY());

    //Balances Robot
    //if (oi.getXboxButtonPress(8)) ballet.balanceRobot(armyBoy); balance is broken

    //Tilts the arm up & down manually
    if (oi.getPOV() == 0) tiltsetpoint += .3;
    else if (oi.getPOV() == 180) tiltsetpoint -= .2;

    //Extends arm in & out manually
    if (oi.getPOV() == 90) extendsetpoint += 0.6;
    else if (oi.getPOV() == 270) extendsetpoint -= 0.6;

    //Arm pid locations - must find all of the pid values
    if (oi.getXboxButtonPress(1) && inSteak.equals("Cube")) { // cube low
      tiltsetpoint = 1;
    }
    else if(oi.getXboxButtonPress(3) && inSteak.equals("Cube")) { //cube mid
      tiltsetpoint = 2;
    }
    else if(oi.getXboxButtonPress(4) && inSteak.equals("Cube")) { //cube high
      tiltsetpoint = 3;
    }
    else if(oi.getXboxButtonPress(1) && inSteak.equals("Cone")) { // cone low 
      tiltsetpoint = 4;
    } 
    else if(oi.getXboxButtonPress(3) && inSteak.equals("Cone")) { // cone mid
      tiltsetpoint = 5;
    } 
    else if(oi.getXboxButtonPress(4) && inSteak.equals("Cone")) { // cone high
      tiltsetpoint = 6;
    }
    else if(oi.getXboxButtonPress(2)) { // human player
      tiltsetpoint = 7;
    }
    armyBoy.updatePID(tiltsetpoint, extendsetpoint);

    // else if(oi.getXboxButtonPress(2)) { // ground both unused at the moment
      
    // }
    // else if(oi.getXboxButtonPress(2)) { // home hopefully
      
    // }
  
    //Controls intake
    if (oi.getXboxButtonPress(6) && oi.getXboxButtonPress(7)) intakeSpeed = .2;
    else if (oi.getXboxButtonPress(6)) intakeSpeed = .6;
    else if (oi.getXboxButtonPress(5)) intakeSpeed = -.15;
    else intakeSpeed = 0;;

    grab.intake(intakeSpeed);

    //Intake out/in
    if (oi.getJoystickButtonPress(5) && !buttonIsPress) {
      grab.toggle();
      buttonIsPress = true;
    }
    else if (!oi.getJoystickButtonPress(5)) buttonIsPress = false;
  }
  
  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
