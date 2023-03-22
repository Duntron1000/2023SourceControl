package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
//import frc.robot.Robot;


//Makes sure the robot is balanced and not tipping
public class balance {
    public final AHRS navX = new AHRS();

    public void balanceRobot(DriveTrain d) {
        if (navX.getPitch() > 2) {
            d.drive(0, .1);
        }
        else if (navX.getPitch() < -2) {
            d.drive(0, -.1);
        }
    }
}

//import is broken 
