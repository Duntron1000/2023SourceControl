package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.portMap;

public class armExtend {
    private static final int deviceID = portMap.CAN_extending;
    private final CANSparkMax m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);
    private final SparkMaxPIDController m_pidController = m_motor.getPIDController();
    public RelativeEncoder m_encoder;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private double rotation = 0;

    public armExtend() {
        //reset motor object
        m_motor.restoreFactoryDefaults();

        //create and reset encoder
        m_encoder = m_motor.getEncoder();
        m_encoder.setPosition(0);

        //PID values -- to be removed
        kP = 0.15; 
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
    }

    public void updatePID(double rotations){
        rotation = rotations;

        //runs the motor to the desired setpoint 
        m_pidController.setReference(rotations, CANSparkMax.ControlType.kPosition);
    }

    public double getRotations() {
        return rotation;
    }
}
