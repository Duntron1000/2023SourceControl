package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class balanceIdea {
    private final AHRS navX = new AHRS();;

    private double driveTrainSetPoint;
    private static final double driveTrainGain = -.6;

    private static final PIDController pid1 = new PIDController(.075, 0, 0);

    public void balance(DriveTrain d){ 
        driveTrainSetPoint = driveTrainGain*pid1.calculate(deadZone(navX.getPitch(), 2.5), 0);

        SmartDashboard.putNumber("DTSP", driveTrainSetPoint);

        d.drive(0, driveTrainSetPoint);
        //motor.set(driveTrainSetPoint);
    }

    private double deadZone(double pitch, double limit){
        if(Math.abs(pitch) < limit || Math.abs(pitch) > 20){
            return 0;
        }else{
            return pitch;
        }
    }

    public static void speedFromAngle(){
        double angle = SmartDashboard.getNumber("Test Angle:", 0);
        SmartDashboard.putNumber("Resulting speed:", driveTrainGain*pid1.calculate(angle, 0));
    }
}
