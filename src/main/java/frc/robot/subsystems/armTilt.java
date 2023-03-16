package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class armTilt {
    private static final int deviceID = 62;
    private CANSparkMax m_motor;
    private SparkMaxPIDController m_pidController;
    private RelativeEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public armTilt(){
        m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

        m_motor.restoreFactoryDefaults();

        m_pidController = m_motor.getPIDController();

        m_encoder = m_motor.getEncoder();

        kP = 0.1; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        SmartDashboard.putNumber("Set Rotations", deviceID);
    }

    public void updatePID(){
        double rotations = SmartDashboard.getNumber("Set Rotations", 0);

        m_pidController.setReference(rotations, CANSparkMax.ControlType.kPosition);
    
        SmartDashboard.putNumber("SetPoint", rotations);
        SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
    }
}
