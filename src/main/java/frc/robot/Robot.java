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

//import javax.print.attribute.standard.Compression;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer; 


/*
The VM is configured to automatically run this class, and to call the functions corresponding to
each mode, as described in the TimedRobot documentation. If you change the name of this class or
the package after creating this project, you must also update the build.gradle file in the
project.
*/
public class Robot extends TimedRobot {

  //Objects
  public static final DriveTrain drive = new DriveTrain();
  private final OIHandler oi = new OIHandler();
  //private final Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);
  
  //private balance ballet = new balance(); //see balance class
  private final AHRS gyro = new AHRS();
  public static final grabber grab = new grabber();
  public static final arm armyBoy = new arm();

  //Important variables
  private double targetTiltsetpoint = 0;
  private double targetExtendsetpoint = 0;
  private double tiltsetpoint;
  private double extendsetpoint;
  private boolean buttonIsPress = false;
  //private boolean failSafeEnable = false;
  private String inSteak;
  private double intakeSpeed = 0;

  //Auton choices
  private static final String kDefaultAuto = "Nothing";
  private static final String kCustomAuto1 = "communityExit";
  private static final String kCustomAuto2 = "oneCone";
  private static final String kCustomAuto3 = "oneCubeExit";
  private static final String kCustomAuto4 = "oneCubeMid";
  private static final String kCustomAuto5 = "oneCubeMidExit";
  private static final String kCustomAuto6 = "engage";
  private static final String kCustomAuto7 = "engageOneCube";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  autonBase autoBoyo;
  
  //Puts all of the data we want onto the smartdashboard
  private void smartdashboards() {
    //Adds Joysticks values
    SmartDashboard.putNumber("Joystick X: ", oi.getJoystickX());
    SmartDashboard.putNumber("Joystick Y: ", oi.getJoystickY());

    //Encoder values
    SmartDashboard.putNumber("Encoder Left: ", drive.getLeftEncoder());
    SmartDashboard.putNumber("Encoder Right: ", drive.getRightEncoder());

    //Setpoints
    SmartDashboard.putNumber("Tilt Set Point: ", tiltsetpoint);
    SmartDashboard.putNumber("Extend Set Point: ", extendsetpoint);
    SmartDashboard.putNumber("Tilt Encoder: ", armyBoy.getTiltEncoder());
    SmartDashboard.putNumber("Extend Encoder: ", armyBoy.getExtendEncoder());

    //Intake state
    if (grab.cubeMode) inSteak = "Cube";
    else inSteak = "Cone";

    SmartDashboard.putString("Intake", inSteak);

    //Intake direcrion
    if (intakeSpeed == .6) SmartDashboard.putString("Intake Direction: ", "Fast Out");
    else if (intakeSpeed == .2) SmartDashboard.putString("Intake Direction: ", "Out");
    else if (intakeSpeed == -.15) SmartDashboard.putString("Intake Direction: ", "In");
    else SmartDashboard.putString("Intake Direction: ", "Stopped");

    //Robot's current pitch
    SmartDashboard.putNumber("Gyro: ", (gyro.getPitch()));

    balanceIdea.speedFromAngle();
  }

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
    m_chooser.addOption("One Cone Mid", kCustomAuto4);
    m_chooser.addOption("One Cone Mid Exit", kCustomAuto5);
    m_chooser.addOption("Engage", kCustomAuto6);
    m_chooser.addOption("Engage One Cone", kCustomAuto7);
    SmartDashboard.putData("Auto choices", m_chooser);

    smartdashboards();

    SmartDashboard.putNumber("Test Angle:", 0);

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
    
    System.out.println("Auto selected: " + m_autoSelected);
    if (m_autoSelected.equals(kCustomAuto1))autoBoyo = new autonBase();
    else if (m_autoSelected.equals(kCustomAuto2)) autoBoyo = new autonOneCone();
    else if (m_autoSelected.equals(kCustomAuto3)) autoBoyo = new autonOneConeExit();
    else if (m_autoSelected.equals(kCustomAuto4)) autoBoyo = new autonOneConeMid();
    else if (m_autoSelected.equals(kCustomAuto5)) autoBoyo = new autonOneConeMidExit();
    else if (m_autoSelected.equals(kCustomAuto6)) autoBoyo = new autonEngage();
    else if (m_autoSelected.equals(kCustomAuto7)) autoBoyo = new autonEngageOneCone();

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
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  /* 
    if(comp.getPressure() < 90){
    comp.enableDigital();
    }
    */

    //Drives using flighstick values
    drive.drive(oi.getJoystickX(), -oi.getJoystickY());

    //Tilts the arm up & down manually
    if (oi.getPOV() == 0) targetTiltsetpoint -= .4;
    else if (oi.getPOV() == 180) targetTiltsetpoint += .3;

    //Extends arm in & out manually
    if (oi.getPOV() == 90) targetExtendsetpoint += 0.6;
    else if (oi.getPOV() == 270) targetExtendsetpoint -= 0.6;

    //Arm pid locations - must find all of the pid values
    if (oi.getXboxButtonPress(1) && inSteak.equals("Cube")) { // cube low
      targetTiltsetpoint = -10.2;
      targetExtendsetpoint = 0;
    }
    else if(oi.getXboxButtonPress(3) && inSteak.equals("Cube")) { //cube mid
      targetTiltsetpoint = -53.6;
      targetExtendsetpoint = 0;
    }
    else if(oi.getXboxButtonPress(4) && inSteak.equals("Cube")) { //cube high
      targetTiltsetpoint = -76;
      targetExtendsetpoint = 0;
    }
    else if(oi.getXboxButtonPress(1) && inSteak.equals("Cone")) { // cone low 
      targetTiltsetpoint = -1;
      targetExtendsetpoint = 0;
    } 
    else if(oi.getXboxButtonPress(3) && inSteak.equals("Cone")) { // cone mid
      targetTiltsetpoint = -72.4;
      targetExtendsetpoint = -419.4;
    } 
    // else if(oi.getXboxButtonPress(4) && inSteak.equals("Cone")) { // cone high
    //   //tiltsetpoint = 6;
    // }
    else if(oi.getXboxButtonPress(2)) { // human player
      targetTiltsetpoint = -73;
      targetExtendsetpoint = -1;
    }

    if (targetTiltsetpoint < tiltsetpoint) {
      tiltsetpoint = targetTiltsetpoint;

      if (Math.abs(targetTiltsetpoint - armyBoy.getTiltEncoder()) < 1) extendsetpoint = targetExtendsetpoint;
    }

    else {
      extendsetpoint = targetExtendsetpoint;

      if (Math.abs(targetExtendsetpoint - armyBoy.getExtendEncoder()) < 1) tiltsetpoint = targetTiltsetpoint;
    } 

    armyBoy.updatePID(tiltsetpoint, extendsetpoint);

    // else if(oi.getXboxButtonPress(2)) { // ground both unused at the moment
      
    // }
    // else if(oi.getXboxButtonPress(2)) { // home hopefully
      
    // }
  
    //Controls intake
    if (oi.getXboxButtonPress(6) && oi.getXboxButtonPress(7)) intakeSpeed = .2;
    else if (oi.getXboxButtonPress(6)) intakeSpeed = .6;
    else if (oi.getXboxButtonPress(5)) intakeSpeed = -.2;
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
}
