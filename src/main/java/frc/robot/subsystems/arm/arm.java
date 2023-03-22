package frc.robot.subsystems.arm;

//Controls the arm
public class arm {

    //armTilt object
    private armTilt tilt;
    //arm extend object
    private armExtend extend; 

    public arm(){
        tilt  = new armTilt();
        extend = new armExtend();
    }

    //updated both tilt and extend PID with the desired position
    public void updatePID(double armAngle, double armLength){
        tilt.updatePID(armAngle);
        extend.updatePID(armLength);
    }

    //returns position of the TiltEncoder
    public double getTiltEncoder() {
        return tilt.m_encoder.getPosition();
    }

    //returns the position of the extetion encoder
    public double getExtendEncoder() {
        return extend.m_encoder.getPosition();
    }
}
