package frc.robot.Auton;

import frc.robot.Robot;
import frc.robot.subsystems.balanceIdea;

public class autonEngageOneCone extends autonBase {
    private double targetPoint;
    private double extendPoint;
    private boolean flagTilt;
    private final balanceIdea bal = new balanceIdea();
    private boolean flagDrive;

    public autonEngageOneCone() {
        targetPoint = 0;
        extendPoint = 0;
        flagTilt = false;
        System.out.println("One");
        Robot.drive.resetEncoders();
        flagDrive = false;
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {
        
        if (!flagTilt) {targetPoint = -20;} 
        if (!flagTilt && Robot.armyBoy.getTiltEncoder() < -19.7) flagTilt = true;
        if (flagTilt && !flagDrive) {
            targetPoint = -5;
            Robot.drive.drive(0, 0.6);
        }
        if (Robot.drive.getLeftEncoder() < -40) flagDrive = true;
        if (flagDrive) bal.balance(Robot.drive);

        Robot.armyBoy.updatePID(targetPoint, extendPoint); 
    }
}
