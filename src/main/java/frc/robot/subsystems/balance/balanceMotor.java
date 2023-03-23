package frc.robot.subsystems.balance;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.portMap;

public class balanceMotor {
    private static final int deviceID = portMap.CAN_TEST;
    private CANSparkMax m_motor;
    private SparkMaxPIDController m_pidController;
    public RelativeEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public balanceMotor() {
        //create motor object
        m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

        //reset motor object
        m_motor.restoreFactoryDefaults();

        // geting the pid controller
        m_pidController = m_motor.getPIDController();

        //create and reset encoder object
        m_encoder = m_motor.getEncoder();
        m_encoder.setPosition(0);

        //PID values -- to be removed
        kP = 0.01; 
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

        //display all PID valued to SmartDashboard -- to be removed after tuning
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    public void updatePID(double rotations){
        // read PID coefficients from SmartDashboard -- to be removed after tuning
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
        m_pidController.setReference(rotations, CANSparkMax.ControlType.kVelocity);
    
        //display setpint and accual position -- to be moved to Robot
        SmartDashboard.putNumber("Tilt SetPoint", rotations);
        SmartDashboard.putNumber("Tilt Encoder", m_encoder.getPosition());
    }
}
