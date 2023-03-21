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

    public void updatePID(double armAngle, double armLength){
        tilt.updatePID(armAngle);
        extend.updatePID(armLength);
    }

    public double getTiltEncoder() {
        return tilt.m_encoder.getPosition();
    }

    public double getExtendEncoder() {
        return extend.m_encoder.getPosition();
    }
}
