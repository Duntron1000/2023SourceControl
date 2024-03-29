package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.portMap;

public class armExtend {
    private static final int deviceID = portMap.CAN_extending;
    private CANSparkMax m_motor;
    private SparkMaxPIDController m_pidController;
    public RelativeEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public armExtend() {
        //create motor object
        m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

        //reset motor object
        m_motor.restoreFactoryDefaults();

        m_pidController = m_motor.getPIDController();

        //create and reset encoder
        m_encoder = m_motor.getEncoder();
        m_encoder.setPosition(0);

        //PID values -- to be removed
        kP = 0.1; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        //set PID values
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        //put PID values into SmartDashboard -- will be removed after tuning
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    public void updatePID(double rotations){
        // read PID coefficients from SmartDashboard -- will be removed after tuning
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }
        if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_pidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
        }


        //runs the motor to the desired setpoint 
        m_pidController.setReference(rotations, CANSparkMax.ControlType.kPosition);
    
        //display setpoint and accual position 
        SmartDashboard.putNumber("Extention SetPoint", rotations);
        SmartDashboard.putNumber("Extention Encoder", m_encoder.getPosition());
    }
}
