package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import frc.robot.portMap;

//Controls the arm
public class arm {
    
    //PID for pivot point
    private PIDController pidExtention = new PIDController(0.2, 0., 0);

    //Motor controller for arm
    private CANSparkMax armExtend = new CANSparkMax(portMap.CAN_extending, MotorType.kBrushless);
    private RelativeEncoder extendEncoder;

    //Motor controller for lazy susan
    private CANSparkMax susan = new CANSparkMax(portMap.CAN_susRotate, MotorType.kBrushless);
    private RelativeEncoder susEncoder;
    
    //Important arm variables
    public double angle = 0;
    public double extensionAmmount = 0;

    public arm(){
        susEncoder = susan.getEncoder();
        susEncoder.setPosition(0);

        extendEncoder = armExtend.getEncoder();
        extendEncoder.setPosition(0);
    }

    //Extends and contracts the arm
    public void extend(double setpoint) {
        armExtend.set(pidExtention.calculate(getExtendEncoder(), setpoint));
    }

    //Rotates the lazy suzan
    public void rotate(double speed) {
        //susan.getEncoder().getPosition();
        susan.set(speed);
    }


    //Gets the encoder position of the lazy susan
    public double getSusanEncoder() {
        return susEncoder.getPosition();
    }

    //Resets sus encoder
    public void resetSus() {
        susEncoder.setPosition(0);
    }

    //Gets encoder for extension motor
    public double getExtendEncoder() {
        return extendEncoder.getPosition();
    }

    //Resets encoder for extension motor
    public void resetExtention() {
        extendEncoder.setPosition(0);
    }
}
