package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;

public class balanceIdea {
    private AHRS navX;

    private PIDController pid;

    public balanceIdea(){
        navX = new AHRS();
        pid = new PIDController(0, 0, 0);
    }

    public void balance(DriveTrain d){ 
        d.drive(0, pid.calculate(navX.getPitch(), 0));
    }


}
