package frc.robot.Auton;

import frc.robot.Robot;

public class autonOneConeExit extends autonBase {
    private double targetPoint;
    private boolean flagTilt;

    public autonOneConeExit() {
        targetPoint = 0;
        flagTilt = false;
        System.out.println("One");
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {

        if (!flagTilt) targetPoint = -20;
        if (!flagTilt && Robot.armyBoy.getTiltEncoder() < -19.7) flagTilt = true;
        if (flagTilt) targetPoint = -5;
        if (flagTilt && Math.abs(Robot.drive.getLeftEncoder()) < 87) {
            Robot.drive.drive(0, .4);
        }

        Robot.armyBoy.updatePID(targetPoint, 0.0);
    }
}
