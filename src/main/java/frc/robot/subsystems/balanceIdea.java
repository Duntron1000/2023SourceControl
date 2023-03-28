package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class balanceIdea {
    private final AHRS navX = new AHRS();;

    private double driveTrainSetPoint;
    private final double driveTrainGain = -.5;

    private final PIDController pid1 = new PIDController(.1, 0, 0);

    public void balance(DriveTrain d){ 
        driveTrainSetPoint = driveTrainGain*pid1.calculate(deadZone(navX.getPitch(), 2), 0);

        SmartDashboard.putNumber("DTSP", driveTrainSetPoint);

        d.drive(0, driveTrainSetPoint);
        //motor.set(driveTrainSetPoint);
    }

    private double deadZone(double pitch, double limit){
        if(Math.abs(pitch) < limit){
            return 0;
        }else{
            return pitch;
        }
    }


}
