package frc.robot.subsystems.balance;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;

public class balanceIdea {
    public AHRS navX;

    private PIDController pid1;
    private balanceMotor m; 

    private double driveTrainSetPoint;
    private double driveTrainGain = .05;


    public balanceIdea(){
        navX = new AHRS();
        pid1 = new PIDController(0.01, 0, 0.065);
        m = new balanceMotor();
        //pid1.setTolerance(2);
    }

    public void balance(DriveTrain d) { 
        driveTrainSetPoint = driveTrainGain*pid1.calculate(deadzone(navX.getPitch(), 1), 0);

        SmartDashboard.putNumber("Drive Point", driveTrainSetPoint);

        m.updatePID(driveTrainSetPoint);
    }

    public double deadzone(double pitch, double limit){
        if(Math.abs(pitch) < limit){
            return 0;
        }
        else {
            return pitch;
        }
    }
}
