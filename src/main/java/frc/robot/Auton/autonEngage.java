package frc.robot.Auton;

import frc.robot.Robot;
import frc.robot.subsystems.balance;

public class autonEngage extends autonBase {
    private balance bal = new balance();
    
    public autonEngage() {
        Robot.drive.resetEncoders();
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {
        if (Math.abs(Robot.drive.getLeftEncoder()) < 31.25) {
            Robot.drive.drive(0, -.4);
        }
        else {
           bal.balanceRobot(Robot.drive);
        }
        //1.9inches per 1 @ .2
        //1.92inches per 1 @ .4
    }
}
