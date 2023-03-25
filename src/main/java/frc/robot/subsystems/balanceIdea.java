package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class balanceIdea {
    private AHRS navX;

    private double driveTrainSetPoint;
    private double driveTrainGain = -0.1;

    private PIDController pid1 = new PIDController(.1, 0, 0);
    private PIDController pid2  = new PIDController(.1, 0, 0);;

    private CANSparkMax motor = new CANSparkMax(62, MotorType.kBrushless);
    private RelativeEncoder enocder = motor.getEncoder();

    public balanceIdea(){
        navX = new AHRS();
    }

    public void balance(DriveTrain d){ 
        //d.drive(0, pid.calculate(navX.getPitch(), 0));
        driveTrainSetPoint = driveTrainGain*pid1.calculate(deadZone(navX.getPitch(), 2), 0);

        SmartDashboard.putNumber("Drive Point", driveTrainSetPoint);

        motor.set(driveTrainSetPoint);
    }

    private double deadZone(double pitch, double limit){
        if(Math.abs(pitch) < limit){
            return 0;
        }else{
            return pitch;
        }
    }


}
