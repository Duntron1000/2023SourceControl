package frc.robot.Auton;

import frc.robot.Robot;

public class autonOneCone extends autonBase {
    private double targetPoint;
    private boolean flagTilt;

    public autonOneCone() {
        targetPoint = 0;
        flagTilt = false;
        System.out.println("One");
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {
        if (!flagTilt) {targetPoint = -20; System.out.println("hi");} 
        if (!flagTilt && Robot.armyBoy.getTiltEncoder() < -19.7) flagTilt = true;
        if (flagTilt) targetPoint = -5;

        Robot.armyBoy.updatePID(targetPoint, 0.0); 
    }
}
