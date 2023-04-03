package frc.robot.Auton;

import frc.robot.Robot;
import frc.robot.subsystems.balanceIdea;

public class autonEngage extends autonBase {
    private final balanceIdea bal = new balanceIdea();
    private boolean flagDrive;
    
    public autonEngage() {
        Robot.drive.resetEncoders();
        flagDrive = false;
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {
        // if (Math.abs(Robot.drive.getLeftEncoder()) < 31.25) {
        //     Robot.drive.drive(0, -.4);
        // }
        // else {
        if (!flagDrive) Robot.drive.drive(0, 0.6);
        if (Robot.drive.getLeftEncoder() < -40) flagDrive = true;
        if (flagDrive) bal.balance(Robot.drive);
        //}
        //1.9inches per 1 @ .2
        //1.92inches per 1 @ .4
    }
}
