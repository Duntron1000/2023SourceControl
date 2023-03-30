package frc.robot.Auton;

import frc.robot.Robot;

public class autonOneCone extends autonBase {
    private double targetPoint;
    private double extendPoint;
    private boolean flagTilt1;
    private boolean flagTilt2;
    private boolean flagleave;

    public autonOneCone() {
        targetPoint = 0;
        extendPoint = 0;
        flagTilt1 = false;
        flagTilt2 = false;
        flagleave = false;
        System.out.println("One");
    }
//frame: 39in, community distance 16 1/4 ft, 17 ft
    public void execute() {
        // targetPoint = -4;
        // if (Robot.armyBoy.getTiltEncoder() < -3.5) flagTilt1 = true;
        // Robot.grab.intake(-.25);
        // if (flagTilt1) targetPoint = -20;
        // if (Robot.armyBoy.getTiltEncoder() < - 19.5) {
        //     flagTilt2 = true;
        //     flagTilt1 = false;
        // }

        // if (flagTilt2) {
        //     targetPoint = -72.4;
        //     extendPoint = -419.4;
        // }

        Robot.grab.intake(-.25);
        targetPoint = -73;
        extendPoint = -419.4;

        if (Robot.armyBoy.getExtendEncoder() < -419 && !flagTilt1) {Robot.grab.toggle(); flagTilt1 = true;}
        if (flagTilt1){Robot.grab.intake(0); extendPoint = 0;}
        if(Robot.armyBoy.getExtendEncoder() > -.5 && flagTilt1) {flagTilt2 = true;}
        if (flagTilt2){targetPoint = -1;}
        if (flagTilt2 && Robot.armyBoy.getTiltEncoder() >- 1.5) {flagleave = true;}
        if (flagleave && Math.abs(Robot.drive.getLeftEncoder()) < 87) {
            Robot.drive.drive(0, .4);
        }
        
        //if (!flagTilt) {targetPoint = -30;} 
        // if (!flagTilt && Robot.armyBoy.getTiltEncoder() < -19.7) flagTilt = true;
        // if (flagTilt) targetPoint = -5;

        Robot.armyBoy.updatePID(targetPoint, extendPoint); 
    }
}
