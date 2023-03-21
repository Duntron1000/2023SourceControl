package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

//Makes sure the robot is balanced and not tipping
public class balance {
    public AHRS navX = new AHRS();

    public void balanceRobot(DriveTrain a) {
        if (navX.getRoll() > 3) {
            while (navX.getRoll() > 3) a.drive(.1, 0);
        }
        else if (navX.getRoll() < -3) {
            while (navX.getRoll() < -3) a.drive(-.1, 0);
        }
    }
}

//import is broken 
